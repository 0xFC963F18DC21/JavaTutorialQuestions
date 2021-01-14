package net.nergi;

public interface Solution {

    /**
     * Returns the header for the solution, which is the problem's name.
     */
    String getName();

    /**
     * Runs the solution to the problem.
     */
    void exec();
}
