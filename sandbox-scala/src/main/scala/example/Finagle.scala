package example

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.finagle.Http.Server
import com.twitter.finagle.{Http, Service, http}
import com.twitter.util.{Await, Duration, Future, FuturePool}
import com.twitter.util.tunable.TunableMap

object Finagle {

  def main(args: Array[String]): Unit = {

    val service = new Service[http.Request, http.Response] {
      def apply(req: http.Request): Future[http.Response] = {
        FuturePool.unboundedPool {
//          throw new RuntimeException("abc")
          try {
            println("start")
//            Thread.sleep(3000)
          } catch {
            case e: InterruptedException => println(s"interrupted ${Thread.currentThread().getName}")
            case all => println(all)
          }
          println("done")
        }.map { _ =>
          println("map")
          http.Response(req.version, http.Status.NotFound)
        }
      }
    }
    val server = Http
      .Server()
      .withHttp2
      .withRequestTimeout(1.seconds)
      .serve(":8080", service)
    Await.ready(server)
  }
}
