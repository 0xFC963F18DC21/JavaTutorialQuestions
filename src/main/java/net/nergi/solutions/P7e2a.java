package net.nergi.solutions;

import net.nergi.Solution;

/**
 * WARNING: this class will force a stack overflow error. I am not responsible for any damage
 * inflicted onto your machine!
 */
@SuppressWarnings("unused")
public class P7e2a implements Solution {

  private static long depth = 0;

  @SuppressWarnings("InfiniteRecursion")
  private static void theStackIsAlreadyDead() {
    ++depth;
    theStackIsAlreadyDead();
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "7e2a: Stack overflow";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    try {
      theStackIsAlreadyDead();
    } catch (StackOverflowError e) {
      System.out.println("Stack overflow error! Recursion depth reached:");
      System.out.println(depth);
    }
  }
}
