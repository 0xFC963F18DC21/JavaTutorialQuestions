package net.nergi.solutions;

import java.util.ArrayList;
import java.util.Random;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P4c70 implements Solution {

  private static final int GENERATED_NUMBERS = 7;

  private final Random randomiser = new Random();

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "4c70: Lottery numbers";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    ArrayList<Integer> numbers = new ArrayList<>();
    for (int i = 0; i < GENERATED_NUMBERS; ++i) {
      int rnd;

      do {
        rnd = randomiser.nextInt(49) + 1;
      } while (numbers.contains(rnd));

      numbers.add(rnd);

      if (i < 6) {
        System.out.println("Number " + (i + 1) + ": " + rnd);
      } else {
        System.out.println("Bonus Number: " + rnd);
      }
    }
  }
}
