package net.nergi.solutions;

import net.nergi.Main;
import net.nergi.Solution;

/** Solution for 98e3. */
@SuppressWarnings("unused")
public class P98e3 implements Solution {

  public static int next(int x) {
    if ((x & 1) == 0) {
      return x / 2;
    } else {
      return 3 * x + 1;
    }
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "98e3: ... 1 4 2 1 4 2 1 ...";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    if (Main.OTHER_ARGS.isEmpty()) {
      System.out.println("This solution requires an integer input.");
      return;
    }

    int x = Integer.parseInt(Main.OTHER_ARGS.get(0));
    while (x != 1) {
      System.out.print(x + " ");
      x = next(x);
    }

    System.out.println(x);
  }
}
