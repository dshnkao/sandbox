package example

import com.twitter.util.{Await, Awaitable, Future, FuturePools, Promise}

object TwitterFutureInterrupt {
  def main(args: Array[String]): Unit = {
    val job1: Future[Unit] = FuturePools.interruptibleUnboundedPool() {
      println("job1 start")
      Thread.sleep(5000)
      println("job1 end")
    }
    val job2: Future[Unit] = FuturePools.interruptibleUnboundedPool() {
      println("job2 start")
      Thread.sleep(5000)
      println("job2 end")
    }
    val job3: Future[Unit] = FuturePools.interruptibleUnboundedPool() {
      println("job3 start")
      Thread.sleep(6000)
      println("job3 end")
    }
    val job4 = job2.flatMap(_ => job3)
    val job5 = Future.collect(Seq(job1, job4))
    job5.raise(new RuntimeException("interrupt"))
//    println(
//      Await.result(job5)
//    )
    Thread.sleep(7000)
  }
}
