package example

import com.twitter.util.{Await, Awaitable, FuturePools, Promise}

object TwitterFutureInterrupt {
  def main(args: Array[String]): Unit = {
    var p = Promise[String]()
    p.setInterruptHandler(e => {
      println("interrupted")
    })
    var job = FuturePools.unboundedPool() {
      Thread.sleep(2000)
      p.setValue("done")
    }
    p.raise(new RuntimeException("interrupt"))
    println(
      Await.result(p)
    )
  }
}
