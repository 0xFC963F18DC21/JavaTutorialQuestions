package net.nergi.solutions;

import java.util.HashSet;
import java.util.Set;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P876b implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "876b: Generics and subclasses";
  }

  /** Runs the solution to the problem. */
  @SuppressWarnings("UnnecessaryLocalVariable")
  @Override
  public void exec() {
    Set<B> setOfB = new HashSet<>();
    Set<? extends A> setOfA = setOfB;

    System.out.println("Please see the source code.");
  }

  public static class A {}

  public static class B extends A {}
}
