package net.nergi.solutions;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.nergi.Main;
import net.nergi.Solution;
import net.nergi.Utils;

@SuppressWarnings("unused")
public class P8a61 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "8a61: Int set";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    // Demo class substituted for this class.
    if (Main.otherArgs.size() < 1) {
      System.out.println(
          "This solution needs an input to determine how many integers it accepts."
      );
      return;
    }

    try {
      final int integers = Integer.parseInt(Main.otherArgs.get(0));
      System.out.printf("Please enter %d integers:\n", integers);

      final IntSet set = readIntegers(integers);
      System.out.printf("Set is of type: %s\n", set.getClass().getName());

      String line = null;

      do {
        try {
          System.out.println("Enter int to test for membership:");
          line = Utils.br.readLine();

          if (line != null) {
            int res = Integer.parseInt(line);

            System.out.printf(
                "Set contains %d: %s\n", res, set.contains(res)
            );
          }
        } catch (IOException e) {
          // Stop taking in input, something went wrong.
          e.printStackTrace();
        } catch (NumberFormatException e) {
          System.out.println("Please input integers only!");
        }
      } while (line != null);

      System.out.println("Goodbye!");
    } catch (NumberFormatException e) {
      System.out.println("Please input integers only!");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static IntSet readIntegers(int n) throws NumberFormatException {
    final List<Integer> ints = Utils
        .getUserLines(n)
        .stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());

    final IntSet output = (n > 10) ? new MemoryEfficientSet() : new SpeedEfficientSet();
    ints.forEach(output::add);

    return output;
  }

  public interface IntSet {

    // Adds the integer x to the set
    void add(int x);

    // If the integer x belongs to the set, it is removed and 'true'
    // is returned.  Otherwise 'false' is returned
    boolean remove(int x);

    // Returns true iff the set is empty
    boolean isEmpty();

    // Returns true iff the set contains the integer x
    boolean contains(int x);

  }

  public static class MemoryEfficientSet implements IntSet {

    private final Set<Integer> backingSet = new HashSet<>();

    @Override
    public void add(int x) {
      backingSet.add(x);
    }

    @Override
    public boolean remove(int x) {
      return backingSet.remove(x);
    }

    @Override
    public boolean isEmpty() {
      return backingSet.isEmpty();
    }

    @Override
    public boolean contains(int x) {
      return backingSet.contains(x);
    }

  }

  public static class SpeedEfficientSet implements IntSet {

    private final HashMap<Integer, Boolean> backingMap = new HashMap<>();

    @Override
    public void add(int x) {
      backingMap.put(x, true);
    }

    @Override
    public boolean remove(int x) {
      return backingMap.remove(x) != null;
    }

    @Override
    public boolean isEmpty() {
      return backingMap.isEmpty();
    }

    @Override
    public boolean contains(int x) {
      final Boolean val = backingMap.get(x);
      return val != null ? val : false;
    }

  }

}
