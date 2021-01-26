package net.nergi.solutions;

import net.nergi.Solution;
import net.nergi.solutions.Pdc38.EmailAddress;
import net.nergi.solutions.Pdc38.GroupAddress;
import net.nergi.solutions.Pdc38.IndividualAddress;

/** Solution for a22c. */
@SuppressWarnings("unused")
public class Pa22c implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "a22c: No duplicate email addresses";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // Try preventing duplicate email addresses.
    try {
      final EmailAddress address1 = new IndividualAddress("a@oops.com");
      final EmailAddress address2 = new IndividualAddress("a@oops.com"); // !!!
    } catch (IllegalArgumentException e) {
      System.out.println("Duplicate email error caught!");
    }

    // Try preventing cyclic groups.
    try {
      final GroupAddress gr1 = new GroupAddress("boopgroup@cutes.org");
      final GroupAddress gr2 = new GroupAddress("nextgroup@arrns.org");

      gr1.addTarget(gr2, true);
      gr2.addTarget(gr1, true); // !!!
    } catch (IllegalArgumentException e) {
      System.out.println("Cyclic addition error caught!");
    }
  }
}
