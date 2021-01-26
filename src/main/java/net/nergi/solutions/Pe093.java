package net.nergi.solutions;

import java.io.IOException;
import java.util.ArrayList;
import net.nergi.Solution;
import net.nergi.Utils;

/** Solution for e093. */
@SuppressWarnings("unused")
public class Pe093 implements Solution {

  private static int safeParsePositiveInt(String s) {
    try {
      final int i = Integer.parseInt(s);

      if (i < 0) {
        throw new NumberFormatException("Negative number");
      }

      return i;
    } catch (NumberFormatException | NullPointerException e) {
      System.out.println("Input ignored. Please enter a positive integer.");
      return -1;
    }
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "e093: Average of numbers";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // Get ints from user
    final ArrayList<Integer> ints = new ArrayList<>();
    final int amount;

    System.out.println("Please input a positive integer:");

    while (true) {
      try {
        final String line = Utils.getBr().readLine();
        final int parsed = safeParsePositiveInt(line);

        if (parsed >= 0) {
          amount = parsed;
          break;
        }
      } catch (IOException e) {
        System.out.println("Input / Output failure, closing program.");
        return;
      }
    }

    System.out.printf("Please input %d positive integers to find their average:\n", amount);

    while (ints.size() < amount) {
      try {
        final String line = Utils.getBr().readLine();
        final int parsed = safeParsePositiveInt(line);

        if (parsed >= 0) {
          ints.add(parsed);
        }
      } catch (IOException e) {
        System.out.println("Input / Output failure, closing program.");
        return;
      }
    }

    System.out.printf("The average of those %d positive integers are:\n", amount);
    System.out.println(
        ints.stream().map(Integer::doubleValue).reduce(Double::sum).orElse(0.0) / amount);
  }
}
