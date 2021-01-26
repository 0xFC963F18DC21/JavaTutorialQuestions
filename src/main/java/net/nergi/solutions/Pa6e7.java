package net.nergi.solutions;

import java.util.Iterator;
import net.nergi.Solution;
import net.nergi.solutions.P8a61.IntSet;
import net.nergi.solutions.P8a61.MemoryEfficientSet;
import net.nergi.solutions.P8a61.SpeedEfficientSet;

/** Solution for a6e7. */
@SuppressWarnings("unused")
public class Pa6e7 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "a6e7: Int set iterators";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // Test new functionality
    final IntSet intSet = new MemoryEfficientSet();

    intSet.add(1);
    intSet.add(4);
    intSet.add(9);
    intSet.add(16);
    intSet.add(25);

    final IntSet otherIntSet = new SpeedEfficientSet();

    otherIntSet.add(1);
    otherIntSet.add(9);
    otherIntSet.add(25);

    System.out.println(intSet);
    System.out.println(otherIntSet);
    System.out.printf("intSet contains otherIntSet: %s\n", intSet.contains(otherIntSet));
    System.out.printf("otherIntSet contains intSet: %s\n", otherIntSet.contains(intSet));
  }

  public interface IntSetIterator extends Iterator<Integer> {

    boolean hasNext();

    Integer next();
  }
}
