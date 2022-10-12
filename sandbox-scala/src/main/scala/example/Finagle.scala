package example

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.finagle.Http.{Http2, Server}
import com.twitter.finagle.http.Version.Http11
import com.twitter.finagle.http.{Method, Request, Response}
import com.twitter.finagle.{Http, Service, http}
import com.twitter.io.Buf.ByteArray
import com.twitter.util.{Await, FuturePool}

object Finagle {
  val foo: Server = Http.Server().withHttp2.withRequestTimeout(1.seconds)
  val bar: Server = Http.Server().withHttp2.withRequestTimeout(4.seconds)
  val barClientHttp1: Service[Request, Response] =
    Http.Client().withNoHttp2.newService("localhost:8081", "barClient")
  val barClientHttp2: Service[Request, Response] =
    Http.Client().withHttp2.newService("localhost:8081", "barClient")
  val serviceFoo: Service[Request, Response] = (req: http.Request) => {
    val client = if (req.params.contains("http1")) barClientHttp1 else barClientHttp2
    val barReq = Request(req.params.toSeq:_*)
    for {
      barResp <- client(barReq)
    } yield {
      val resp = Response()
      resp.content = ByteArray.Owned("foo".getBytes()) concat barResp.content
      resp
    }
  }
  val serviceBar: Service[Request, Response] = (req: http.Request) => FuturePool.interruptibleUnboundedPool {
    try {
      val sleep = req.params.getOrElse("sleep", "0")
      Thread.sleep(Integer.parseInt(sleep))
    } catch {
      case e: Throwable => println(e)
    }
    val resp = Response()
    resp.content = ByteArray.Owned("bar".getBytes())
    resp
  }
  def main(args: Array[String]): Unit = {
    val wait = foo.serve(":8080", serviceFoo)
    bar.serve(":8081", serviceBar)
    Await.ready(wait)
  }
}
