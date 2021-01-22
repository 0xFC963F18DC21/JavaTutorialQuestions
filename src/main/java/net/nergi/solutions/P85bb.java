package net.nergi.solutions;

import java.util.Iterator;
import net.nergi.Solution;
import net.nergi.solutions.P1486.StringStack;
import net.nergi.solutions.P1486.StringStackList;

@SuppressWarnings("unused")
public class P85bb implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "85bb: String stack iterators";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    // Testing the iterator
    final StringStack sstkl = new StringStackList();

    sstkl.push("win");
    sstkl.push("cannot");
    sstkl.push("you");
    sstkl.push("fight");
    sstkl.push("a");
    sstkl.push("is");
    sstkl.push("this");

    for (String s : sstkl) {
      System.out.println(s);
    }

    System.out.println(sstkl);
  }

  /**
   * Gives a top-down iterator for a string stack. Does not empty the stack.
   */
  public interface StringStackIterator extends Iterator<String> {

    boolean hasNext();

    String next();

  }

}
