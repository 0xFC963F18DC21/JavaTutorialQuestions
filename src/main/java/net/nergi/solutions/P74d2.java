package net.nergi.solutions;

import net.nergi.Solution;

@SuppressWarnings("unused")
public class P74d2 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "74d2: Exceptions and inheritance (i)";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    System.out.println("There is no Output: Wrong Solutions");
  }

  /*
   * Originally, this compiles.
   *
   * Adding super.foo() in B, it no longer compiles due to an unreported exception.
   * We can fix this by reporting the exception in the method signature.
   *
   */

  public static class A {

    public void foo() throws Exception {
      throw new Exception();
    }

  }

  public static class B extends A {

    @Override
    public void foo() throws Exception {
      super.foo();
    }

  }

}
