package example

import com.twitter.util.Promise


object Main {
  def main(args: Array[String]): Unit = {
    val promise = Promise[String]()
    promise.setException(new RuntimeException("abc"))
    promise.onFailure { e =>
      println(e);
    }


    val p2 = Promise[String]()
    p2.setException(new RuntimeException("def"))
    val f2 = p2.handle((e: Throwable) => {
      println(e)
    })
    println(f2)

  }
}
