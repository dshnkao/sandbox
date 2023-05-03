package com.denniskao.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class PatternCompile {

    private Pattern pattern;
    private String regex;
    private String s;
    private String empty;

    @Benchmark
    public String nocompile() {
        return pattern.matcher(s).replaceAll(empty);
    }
    @Benchmark
    public String compile() {
        return s.replaceAll(regex, empty);
    }
    @Benchmark
    public String noregex() {
        return s.replace("\"", "");
    }

    @Setup
    public void setup() {
        regex = "^\"|\"$";
        pattern = Pattern.compile(regex);
        s = "\"android\"";
        empty = "";
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PatternCompile.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
