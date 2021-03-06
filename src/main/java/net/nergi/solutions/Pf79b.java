package net.nergi.solutions;

import net.nergi.Solution;

/** Solution for f79b. */
@SuppressWarnings("unused")
public class Pf79b implements Solution {

  private static final int ITERATIONS = 1500;

  public static boolean isPalindromic(String str) {
    boolean palindromic = true;
    for (int i = 0; palindromic && i < str.length() / 2; ++i) {
      palindromic = str.charAt(i) == str.charAt(str.length() - 1 - i);
    }

    return palindromic;
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "f79b: Perfect palindromic cubes";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    for (int i = 0; i < ITERATIONS; ++i) {
      int cubed = i * i * i;

      if (isPalindromic(Integer.toString(cubed))) {
        System.out.println(i + " cubed is " + cubed);
      }
    }
  }
}
