package net.nergi.solutions;

import net.nergi.Main;
import net.nergi.Solution;

/** Solution for 153d. */
@SuppressWarnings("unused")
public class P153d implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "153d: Exceptions and inheritance (iii)";
  }

  /** Runs the solution to the problem. */
  @SuppressWarnings("ThrowablePrintedToSystemOut")
  @Override
  public void exec() {
    try {
      try {
        try {
          try {
            /*
             * 3 prints No exception was thrown.
             * 1.2 prints The command-line argument you entered was not an integer!
             *
             * 0 prints just the A exception message.
             * 1 prints both the B and the A exception messages.
             * 2 prints all three of A's, B's and C's exception messages.
             * ... and then all three of these will print the general exception catch's
             * message.
             *
             * No arguments result in a caught ArrayIndexOutOfBoundsException that
             * prints An exception was thrown: [exception message].
             *
             * After all that, the print in the finally block is executed.
             *
             */

            switch (Integer.parseInt(Main.otherArgs.get(0))) {
              case 0:
                throw new A();
              case 1:
                throw new B();
              case 2:
                throw new C();
              default:
                // Nothing
            }
          } catch (C exception) {
            System.out.println(exception);
            throw exception;
          }
        } catch (B exception) {
          System.out.println(exception);
          throw exception;
        }
      } catch (A exception) {
        System.out.println(exception);
        throw exception;
      }

      System.out.println("No exception was thrown.");

    } catch (NumberFormatException exception) {
      System.out.println("The command-line argument you entered was not an integer!");
    } catch (Exception exception) {
      System.out.println("An exception was thrown: " + exception);
    } finally {
      System.out.println("All control-flow paths get to me!");
    }
  }

  public static class A extends Exception {

    @Override
    public String toString() {
      return "exception A";
    }
  }

  public static class B extends A {

    @Override
    public String toString() {
      return "exception B is an " + super.toString();
    }
  }

  public static class C extends B {

    @Override
    public String toString() {
      return "exception C is an " + super.toString();
    }
  }
}
