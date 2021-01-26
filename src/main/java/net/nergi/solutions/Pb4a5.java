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

  private static class A {

    static int numCollected = 0;
    private final int id;

    public A(int id) {
      this.id = id;
    }

    @Override
    protected void finalize() {
      System.out.printf("Instance of A with id %d has been GCed!\n", id);
      ++numCollected;
    }
  }
}
