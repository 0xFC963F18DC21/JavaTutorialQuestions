package net.nergi.solutions;

import net.nergi.Solution;
import net.nergi.util.Utils;
import net.nergi.solutions.Pb401.GenericSet;
import net.nergi.solutions.Pb401.MemoryEfficientGenericSet;
import net.nergi.solutions.Pb401.SpeedEfficientGenericSet;

/** Solution for 336b. */
@SuppressWarnings("unused")
public class P336b implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "336b: Evolving the Set interface";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // Make large maps
    final Runnable toTest =
        () -> {
          final GenericSet<Integer> ints1 = new MemoryEfficientGenericSet<>();
          final GenericSet<Integer> ints2 = new MemoryEfficientGenericSet<>();
          final int amount = 100000;

          for (int i = 0; i < amount; ++i) {
            if (i <= (amount / 3 * 2)) {
              ints1.add(i);
            }

            if (i >= (amount / 3)) {
              ints2.add(i);
            }
          }

          ints1.addAll(ints2);
        };

    Utils.benchmarkTime(toTest);

    // Test immutability
    try {
      final GenericSet<String> strs = new SpeedEfficientGenericSet<>();
      strs.add("this is fine");

      final GenericSet<String> unmodStrs = strs.asUnmodifiableSet();
      unmodStrs.add("this is not fine");
    } catch (UnsupportedOperationException e) {
      System.out.println("Mutable method use caught!");
    }
  }
}
