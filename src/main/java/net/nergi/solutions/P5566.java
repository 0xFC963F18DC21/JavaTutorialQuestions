package net.nergi.solutions;

import java.util.EmptyStackException;
import net.nergi.Solution;
import net.nergi.solutions.P2ffc.GenericStackArray;
import net.nergi.solutions.P2ffc.GenericStackList;

@SuppressWarnings("unused")
public class P5566 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "5566: Exception-throwing stacks";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    try {
      // Test overflowing the array-backed stack
      final GenericStackArray<Integer> ints = new GenericStackArray<>(3);

      ints.push(0);
      ints.push(1);
      ints.push(2);
      ints.push(3); // !!!
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("AIOOB Exception caught!");
    }

    try {
      // Test underflowing the array-backed stack
      final GenericStackArray<Integer> ints = new GenericStackArray<>(3);

      ints.push(0);
      ints.pop();
      ints.pop(); // !!!
    } catch (EmptyStackException e) {
      System.out.println("ES Exception caught!");
    }

    try {
      // Test underflowing the list-backed stack
      final GenericStackList<Integer> ints = new GenericStackList<>();

      ints.push(0);
      ints.pop();
      ints.pop(); // !!!
    } catch (EmptyStackException e) {
      System.out.println("ES Exception caught!");
    }
  }
}
