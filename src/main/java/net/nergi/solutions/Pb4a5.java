package net.nergi.solutions;

import net.nergi.Solution;

/** Solution for b4a5. */
@SuppressWarnings({"unused", "deprecation"})
public class Pb4a5 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "b4a5: Observing the garbage collector";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    for (int i = 0; i < 1000000; ++i) {
      new A(i);
    }

    System.out.printf("%d / 1000000\n", A.numCollected);
  }

  /** Mostly empty class used to observe the garbage collector's behaviour. */
  private static class A {

    /** The number of {@link A} instances collected by GC. */
    public static int numCollected = 0;

    private final int id;

    /** Construct an instance with the given id. */
    public A(int id) {
      this.id = id;
    }

    /**
     * In order to observe the garbage collector, we need to override the finalizer method. However,
     * this will slow down the performance of the object, and is not guaranteed to be called due to
     * the unpredictable behaviour of the garbage collector. Hence why this method was finally
     * deprecated.
     *
     * <p>Note: when it is said that the GC is unpredictable, that is because the implementation of
     * the GC varies depending on the JVM implementation, which can vary between releases due to the
     * differing natures of the operating systems and CPU architectures.
     */
    @Override
    protected void finalize() {
      System.out.printf("Instance of A with id %d has been GCed!\n", id);
      ++numCollected;
    }
  }
}
