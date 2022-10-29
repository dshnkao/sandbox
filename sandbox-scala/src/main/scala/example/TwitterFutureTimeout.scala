package example

import com.twitter.util.{Await, Awaitable, Duration, Future, FuturePool, ScheduledThreadPoolTimer, Timer}

object TwitterFutureTimeout {
  def main(args: Array[String]): Unit = {
    val job = FuturePool.interruptibleUnboundedPool {
      println(Thread.currentThread().getName)
      Thread.sleep(2000)
      println(Thread.currentThread().getName)
    }
      .within(Duration.fromSeconds(1))(new ScheduledThreadPoolTimer())
      .rescue(ex => {
        println(Thread.currentThread().getName)
        Future.value(())
      })


    Await.result(job)

    Thread.sleep(1000)
  }
}
