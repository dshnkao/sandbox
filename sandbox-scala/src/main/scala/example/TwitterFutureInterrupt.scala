package example

import com.twitter.util.{Await, Awaitable, Future, FuturePools, Promise}

object TwitterFutureInterrupt {
  def main(args: Array[String]): Unit = {
    val job1: Future[Unit] = FuturePools.interruptibleUnboundedPool() {
      println("job1 start")
      Thread.sleep(5000)
      println("job1 end")
    }
    job1.raise(new RuntimeException("interrupt"))
    println(
      Await.result(job1)
    )
  }
}
