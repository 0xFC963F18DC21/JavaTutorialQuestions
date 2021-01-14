package net.nergi.solutions;

import net.nergi.Solution;

@SuppressWarnings("unused")
public class Pf79b implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "f79b: Perfect palindromic cubes";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    for (int i = 0; i < 1500; ++i) {
      int cubed = i * i * i;

      if (isPalindromic(Integer.toString(cubed))) {
        System.out.println(i + " cubed is " + cubed);
      }
    }
  }

  public static boolean isPalindromic(String str) {
    boolean palindromic = true;
    for (int i = 0; palindromic && i < str.length() / 2; ++i) {
      palindromic = str.charAt(i) == str.charAt(str.length() - 1 - i);
    }

    return palindromic;
  }
}
