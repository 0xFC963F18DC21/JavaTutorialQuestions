package net.nergi.solutions;

import net.nergi.Main;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P98e3 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "98e3: ... 1 4 2 1 4 2 1 ...";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    if (Main.otherArgs.isEmpty()) {
      System.out.println("This solution requires an integer input.");
      return;
    }

    int x = Integer.parseInt(Main.otherArgs.get(0));
    while (x != 1) {
      System.out.print(x + " ");
      x = next(x);
    }

    System.out.println(x);
  }

  public static int next(int x) {
    if ((x & 1) == 0) {
      return x / 2;
    } else {
      return 3 * x + 1;
    }
  }
}
