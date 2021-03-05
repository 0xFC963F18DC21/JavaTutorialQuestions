package net.nergi.misc.objects.synchro.stack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import net.nergi.util.Utils;
import net.nergi.util.ExceptionSuppressor;

/** Creates rough performance tests for stack classes implementing {@link S0000Stack}. */
public class StackPerformanceTester {

  private StackPerformanceTester() {}

  /**
   * Make a new tester for a given stack class.
   *
   * @param stackClass Class of stack to generate
   * @param <E>        Type of element inside stack
   * @return Tester object for a stack class
   */
  public static <E, S extends S0000Stack<E>> Tester generateTesterUnit(
      Class<S> stackClass, Supplier<E> dummyValueGenerator) {
    return new Tester() {
      private final Supplier<S> emptyStackSupplier =
          () -> {
            try {
              final Constructor<S> ctor = stackClass.getDeclaredConstructor();
              return (S) ctor.newInstance();
            } catch (NoSuchMethodException
                | InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) {
              throw new IllegalStateException(e);
            }
          };

      private final Supplier<ExecutorService> threadPoolGenerator =
          () -> Executors.newFixedThreadPool(THREAD_COUNT);

      @Override
      public void test6400Pushes() {
        // Make the thread pool and stack to test.
        final ExceptionSuppressor suppressor = new ExceptionSuppressor();
        final var pool = threadPoolGenerator.get();
        final var stack = emptyStackSupplier.get();

        // Test the runtime for the stack operations, printing the final stack size.
        Utils.benchmarkTime(
            () -> {
              try {
                for (int i = 0; i < ITEM_COUNT; ++i) {
                  pool.execute(
                      () -> suppressor.suppressedExecute(
                          () -> stack.push(dummyValueGenerator.get())));
                }

                pool.shutdown();

                if (pool.awaitTermination(60L, TimeUnit.SECONDS)
                    && suppressor.isCleanExecution()) {
                  System.out.println("\nTesting successful of 6400 pushes.");
                  System.out.println("Final size: " + stack.size() + " / 6400");
                } else {
                  System.out.printf(
                      "%nFailed to terminate / %d exceptions were caught.%n",
                      suppressor.getSuppressedCount()
                  );
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }, 1);
      }

      @Override
      public void test6400Pops() {
        // Make the thread pool and stack to test.
        final ExceptionSuppressor suppressor = new ExceptionSuppressor();
        final var pool = threadPoolGenerator.get();
        final var stack = emptyStackSupplier.get();

        // Pre-generate items to push into the stack.
        for (int i = 0; i < ITEM_COUNT; ++i) {
          stack.push(dummyValueGenerator.get());
        }

        // Test the runtime for the stack operations, printing the final stack size.
        Utils.benchmarkTime(
            () -> {
              try {
                for (int i = 0; i < ITEM_COUNT; ++i) {
                  pool.execute(
                      () -> suppressor.suppressedExecute(stack::pop));
                }

                pool.shutdown();

                if (pool.awaitTermination(60L, TimeUnit.SECONDS)
                    && suppressor.isCleanExecution()) {
                  System.out.println("\nTesting successful of 6400 pops.");
                  System.out.println("Final size: " + stack.size() + " / 0");
                } else {
                  System.out.printf(
                      "%nFailed to terminate / %d exceptions were caught.%n",
                      suppressor.getSuppressedCount()
                  );
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }, 1);
      }

      @Override
      public void test3200Each() {
        // Make the thread pool and stack to test.
        final ExceptionSuppressor suppressor = new ExceptionSuppressor();
        final var pool = threadPoolGenerator.get();
        final var stack = emptyStackSupplier.get();

        for (int i = 0; i < ITEM_COUNT / 2; ++i) {
          stack.push(dummyValueGenerator.get());
        }

        // Test the runtime for the stack operations, printing the final stack size.
        Utils.benchmarkTime(
            () -> {
              try {
                for (int i = 0; i < ITEM_COUNT; ++i) {
                  if (i < 3200) {
                    pool.execute(
                        () -> suppressor.suppressedExecute(stack::pop));
                  } else {
                    pool.execute(
                        () -> suppressor.suppressedExecute(
                            () -> stack.push(dummyValueGenerator.get())));
                  }
                }

                pool.shutdown();

                if (pool.awaitTermination(60L, TimeUnit.SECONDS)
                    && suppressor.isCleanExecution()) {
                  System.out.println("\nTesting successful of 3200 each of pushes and pops.");
                  System.out.println("Final size: " + stack.size() + " / 3200");
                } else {
                  System.out.printf(
                      "%nFailed to terminate / %d exceptions were caught.%n",
                      suppressor.getSuppressedCount()
                  );
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }, 1);
      }

      @Override
      public void test6000Pops400Pushes() {
        // Make the thread pool and stack to test.
        final ExceptionSuppressor suppressor = new ExceptionSuppressor();
        final var pool = threadPoolGenerator.get();
        final var stack = emptyStackSupplier.get();

        for (int i = 0; i < 6000; ++i) {
          stack.push(dummyValueGenerator.get());
        }

        // Test the runtime for the stack operations, printing the final stack size.
        Utils.benchmarkTime(
            () -> {
              try {
                for (int i = 0; i < ITEM_COUNT; ++i) {
                  if (i % 16 == 0) {
                    pool.execute(
                        () -> suppressor.suppressedExecute(
                            () -> stack.push(dummyValueGenerator.get())));
                  } else {
                    pool.execute(
                        () -> suppressor.suppressedExecute(stack::pop));
                  }
                }

                pool.shutdown();

                if (pool.awaitTermination(60L, TimeUnit.SECONDS)
                    && suppressor.isCleanExecution()) {
                  System.out.println("\nTesting successful of 6000 pops and 400 pushes.");
                  System.out.println("Final size: " + stack.size() + " / 400");
                } else {
                  System.out.printf(
                      "%nFailed to terminate / %d exceptions were caught.%n",
                      suppressor.getSuppressedCount()
                  );
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }, 1);
      }

      @Override
      public void test400Pops6000Pushes() {
        // Make the thread pool and stack to test.
        final ExceptionSuppressor suppressor = new ExceptionSuppressor();
        final var pool = threadPoolGenerator.get();
        final var stack = emptyStackSupplier.get();

        for (int i = 0; i < 400; ++i) {
          stack.push(dummyValueGenerator.get());
        }

        // Test the runtime for the stack operations, printing the final stack size.
        Utils.benchmarkTime(
            () -> {
              try {
                for (int i = 0; i < ITEM_COUNT; ++i) {
                  if (i % 16 == 15) {
                    pool.execute(
                        () -> suppressor.suppressedExecute(stack::pop));
                  } else {
                    pool.execute(
                        () -> suppressor.suppressedExecute(
                            () -> stack.push(dummyValueGenerator.get())));
                  }
                }

                pool.shutdown();

                if (pool.awaitTermination(60L, TimeUnit.SECONDS)
                    && suppressor.isCleanExecution()) {
                  System.out.println("\nTesting successful of 400 pops and 6000 pushes.");
                  System.out.println("Final size: " + stack.size() + " / 6000");
                } else {
                  System.out.printf(
                      "%nFailed to terminate / %d exceptions were caught.%n",
                      suppressor.getSuppressedCount()
                  );
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }, 1);
      }
    };
  }

  /**
   * Provides methods that test the concurrent performance and accuracy of a stack class. The
   * methods attempt to normalise the tests by using 16 threads for a test, and also uses a thread
   * pool to make the execution method the same.
   */
  public interface Tester {

    /** Max items in stack. */
    int ITEM_COUNT = 6400;

    /** Threads used for test. */
    int THREAD_COUNT = 16;

    /** Test 6400 concurrent stack pushes. */
    void test6400Pushes();

    /** Test 6400 concurrent stack pops. */
    void test6400Pops();

    /** Test 3200 concurrent stack pushes and pops. */
    void test3200Each();

    /** Test 6000 concurrent stack pops and 400 concurrent pushes. */
    void test6000Pops400Pushes();

    /** Test 400 concurrent stack pops and 6000 concurrent pushes. */
    void test400Pops6000Pushes();
  }
}
