package example

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.finagle.Http.{Client, client}
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}

import java.util.concurrent.{CountDownLatch, Executors, ThreadFactory}

object FinagleClient {
  private val latch = new CountDownLatch(3)

  val clientBase: Client = Client()
    .withSessionPool.minSize(10)
    .withSessionPool.maxSize(10)
    .withSessionPool.ttl(10.seconds)
    .withTls("www.canva.com")

  val serv1: Service[Request, Response] = clientBase
    .withExecutionOffloaded(Executors.newSingleThreadExecutor((r: Runnable) => new Thread(r, "thread-serv1")))
    .newService("www.canva.com:443", "canva/healthz")
  val serv2: Service[Request, Response] = clientBase
    .withExecutionOffloaded(Executors.newSingleThreadExecutor((r: Runnable) => new Thread(r, "thread-serv2")))
    .newService("www.canva.com:443", "canva/healthz")

  def main(args: Array[String]): Unit = {

    serv1.apply(Request("/healthz"))
      .map { resp =>
        println(Thread.currentThread().getName)
        println(resp)
      }
      .ensure(latch.countDown())
    serv2.apply(Request("/healthz"))
      .map { resp =>
        println(Thread.currentThread().getName)
        println(resp)
      }
      .ensure(latch.countDown())

    println("")
    latch.await()
//    client.close()
  }
}
