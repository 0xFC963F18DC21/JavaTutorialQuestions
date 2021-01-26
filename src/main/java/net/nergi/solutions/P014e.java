package net.nergi.solutions;

import java.util.Random;
import net.nergi.Main;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P014e implements Solution {

  private final long seed = System.nanoTime();
  private final Random randomiser = new Random(seed);

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "014e: Random numbers";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    if (Main.otherArgs.isEmpty()) {
      System.out.println("This solution requires an integer input.");
      return;
    }

    int upperBound = Integer.parseInt(Main.otherArgs.get(0));
    int amount = 0;

    // We are relying on the fact that the default value of primitive booleans is false.
    boolean[] generated = new boolean[upperBound];

    System.out.println("Seed: " + seed);
    System.out.println("Generating random numbers:");
    while (!boolArrayAnd(generated)) {
      int next = randomiser.nextInt(upperBound);
      System.out.print(next + " ");

      generated[next] = true;
      ++amount;
    }

    System.out.println(
        "\nI had to generate "
            + amount
            + " random numbers between 0 and "
            + (upperBound - 1)
            + " to have produced all such numbers at least once.");
  }

  private boolean boolArrayAnd(boolean[] bools) {
    boolean result = true;
    for (int i = 0; result && i < bools.length; ++i) {
      result = bools[i];
    }

    return result;
  }
}
