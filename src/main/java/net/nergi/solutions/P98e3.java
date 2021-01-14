package net.nergi.solutions;

import net.nergi.Main;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P98e3 implements Solution {

  @Override
  public String getName() {
    return "98e3: ... 1 4 2 1 4 2 1 ...";
  }

  @Override
  public void exec() {
    if (Main.secondArg.isEmpty()) {
      System.out.println("This solution needs an integer as an input.");
      return;
    }

    int x = Integer.parseInt(Main.secondArg);
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
