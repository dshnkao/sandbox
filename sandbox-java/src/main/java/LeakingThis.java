import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LeakingThis {

  static class Foo {

    final Integer i;

    Foo() {
      var bar = new Bar() {
        @Override
        void doBar() {
          Foo.this.doFoo();
        }
      };
      bar.doBar();
      this.i = 5;
    }

    void doFoo() {
      System.out.println(this.i);
    }
  }

  static class Bar {

    void doBar() {
    }
  }

  public static void main(String[] args) throws InterruptedException {
    var x = new Foo();
  }
}
