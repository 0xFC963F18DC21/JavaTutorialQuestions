package net.nergi.solutions;

import java.util.Iterator;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P735a implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "735a: Generic iterators";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    System.out.println("Oops, I've already done most of the work in Pb401 and P2ffc...");
  }

  /** General iterator class for any reference type. */
  public interface GenericIterator<T> extends Iterator<T> {}

  /** Generic collection for any reference type. */
  public interface GenericCollection<T> extends Iterable<T> {

    GenericIterator<T> iterator();
  }
}
