package com.denniskao.concurrency;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * https://openjdk.org/jeps/193
 *
 * Save memory over AtomicInteger
 */
@State(Scope.Thread)
@Fork(value = 1, jvmArgs = {"-Xms256m", "-Xmx256m", "-XX:+UseG1GC"})
@Warmup(iterations = 3, time = 3)
@Measurement(iterations = 5, time = 5)
@Threads(4)
public class VarHandleBenchmark {

    // array option
    private final AtomicIntegerArray array = new AtomicIntegerArray(1);

    // vanilla AtomicInteger
    private final AtomicInteger counter = new AtomicInteger();

    // count field and its VarHandle
    private volatile int count;
    private static final VarHandle COUNT;

    // count2 field and its field updater
    private volatile int count2;
    private static final AtomicIntegerFieldUpdater<VarHandleBenchmark> COUNT2 ;

    static {
        try {
            COUNT = MethodHandles.lookup()
                    .findVarHandle(VarHandleBenchmark.class, "count", Integer.TYPE);
            COUNT2 = AtomicIntegerFieldUpdater.newUpdater(VarHandleBenchmark.class, "count2");
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    @Benchmark
    public void atomic(Blackhole bh) {
        bh.consume(counter.getAndAdd(1));
    }

    @Benchmark
    public void atomicArray(Blackhole bh) {
        bh.consume(array.getAndAdd(0, 1));
    }

    @Benchmark
    public void varhandle(Blackhole bh) {
        bh.consume((int) COUNT.getAndAdd(this, 1));
    }

    @Benchmark
    public void fieldUpdater(Blackhole bh) {
        bh.consume(COUNT2.getAndAdd(this, 1));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(VarHandleBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
