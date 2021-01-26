package net.nergi.solutions;

import net.nergi.Solution;

/** Solution for 2862. */
@SuppressWarnings("unused")
public class P2862 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "2862: Exceptions and inheritance (ii)";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    final A myB = new B();
    try {
      myB.foo();
    } catch (MyExceptionC e) {
      System.out.println("Exception of type MyExceptionC was thrown.");
    }
  }

  /*
   * Originally, this compiles.
   *
   * Changing B to throw an IOException does not work because it is not a subclass of
   * MyExceptionC.
   *
   * The demo main does not compile because the apparent type of myB is A, and A throws
   * the class MyExceptionC, in which MyExceptionD is not a superclass of.
   *
   */

  public static class A {

    void foo() throws MyExceptionC {
      throw new MyExceptionC();
    }
  }

  public static class B extends A {

    @Override
    void foo() throws MyExceptionD {
      throw new MyExceptionD();
    }
  }

  public static class MyExceptionC extends Exception {}

  public static class MyExceptionD extends MyExceptionC {}
}
