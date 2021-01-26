package net.nergi;

/**
 * A class that represents a solution for one of the questions / problems. Note that all classes
 * that implement this interface will have the @SuppressWarnings("unused") annotation as they will
 * be used at runtime via reflection (See Main.java for details).
 */
public interface Solution {

  /** Returns the header for the solution, which is the problem's name. */
  String getName();

  /** Runs the solution to the problem. */
  void exec();
}
