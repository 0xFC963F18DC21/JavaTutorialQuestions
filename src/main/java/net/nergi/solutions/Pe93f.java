package net.nergi.solutions;

import net.nergi.Solution;

@SuppressWarnings("unused")
public class Pe93f implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "e93f: Apparent and actual types";
  }

  /** Runs the solution to the problem. */
  @SuppressWarnings("TextBlockMigration")
  @Override
  public void exec() {
    System.out.println(
        "The accept method in D is treated as an overload of the accept method in C.\n"
            + "In the case of overloads, Java tries to use the most specialised method that a type"
            + " offers.\n"
            + "This means calling .accept() in C will use C's method, but using D's .accept()"
            + " causes Java to use the most specific type possible, in this case, it will use the"
            + " overload that takes in a value of type B.");

    // -----------------------------------------------------

    B b = new B();

    C c = new D();
    c.accept(b);

    D d = new D();
    d.accept(b);
  }

  // -----------------------------------------------------------------------------------------------

  public static class A {}

  public static class B extends A {}

  public static class C {

    public void accept(A a) {
      System.out.println("Accepted object of type A.");
    }
  }

  public static class D extends C {

    public void accept(B b) {
      System.out.println("Accepted object of type B.");
    }
  }
}
