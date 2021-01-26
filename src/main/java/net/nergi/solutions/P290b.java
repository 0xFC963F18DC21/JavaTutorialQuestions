package net.nergi.solutions;

import net.nergi.Solution;
import net.nergi.solutions.P1ae9.Point;

@SuppressWarnings("unused")
public class P290b implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "290b: Memory leaks in Java";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    System.out.println("No more memory leaks!");

    for (int i = 0; i < 10000000; ++i) {
      Point.makePoint(i * 2, i * 3, i * 5);
    }

    System.out.println("See, no OOME!");
  }
}
