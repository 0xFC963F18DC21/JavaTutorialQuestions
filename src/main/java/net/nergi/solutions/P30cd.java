package net.nergi.solutions;

import java.util.ArrayList;
import net.nergi.Solution;

/**
 * Solution for 30cd. WARNING: this class will force a Heap OOM Error. I am not responsible for any
 * damage inflicted onto your machine!
 */
@SuppressWarnings("unused")
public class P30cd implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "30cd: Heap exhaustion";
  }

  /** Runs the solution to the problem. */
  @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "InfiniteLoopStatement"})
  @Override
  public void exec() {
    long amount = 0L;

    try {
      // Let's do this!
      final ArrayList<Long> longs = new ArrayList<>();

      while (true) {
        longs.add(amount++);
      }
    } catch (OutOfMemoryError e) {
      System.out.println("OOM Error! Integers created:");
      System.out.println(amount);
    }
  }
}
