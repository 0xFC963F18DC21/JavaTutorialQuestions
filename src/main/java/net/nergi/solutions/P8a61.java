package net.nergi.solutions;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.nergi.Main;
import net.nergi.Solution;
import net.nergi.util.Utils;
import net.nergi.solutions.Pa6e7.IntSetIterator;

/** Solution for 8a61. */
@SuppressWarnings("unused")
public class P8a61 implements Solution {

  public static IntSet readIntegers(int n) throws NumberFormatException {
    final List<Integer> ints =
        Utils.getUserLines(n).stream().map(Integer::parseInt).collect(Collectors.toList());

    final IntSet output = (n > 10) ? new MemoryEfficientSet() : new SpeedEfficientSet();
    ints.forEach(output::add);

    return output;
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "8a61: Int set";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // Demo class substituted for this class.
    if (Main.OTHER_ARGS.size() < 1) {
      System.out.println("This solution needs an input to determine how many integers it accepts.");
      return;
    }

    try {
      final int integers = Integer.parseInt(Main.OTHER_ARGS.get(0));
      System.out.printf("Please enter %d integers:\n", integers);

      final IntSet set = readIntegers(integers);
      System.out.printf("Set is of type: %s\n", set.getClass().getName());

      String line = null;

      do {
        try {
          System.out.println("Enter int to test for membership:");
          line = Utils.getBr().readLine();

          if (line != null) {
            int res = Integer.parseInt(line);

            System.out.printf("Set contains %d: %s\n", res, set.contains(res));
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

  public interface IntSet extends Iterable<Integer> {

    // Adds the integer x to the set
    void add(int x);

    // If the integer x belongs to the set, it is removed and 'true'
    // is returned.  Otherwise 'false' is returned
    boolean remove(int x);

    // Returns true iff the set is empty
    boolean isEmpty();

    // Returns true iff the set contains the integer x
    boolean contains(int x);

    // Extension for 8a61
    IntSetIterator iterator();

    // Add to the set each element in 'other'
    default void addAll(IntSet other) {
      for (Integer i : other) {
        add(i);
      }
    }

    // Remove from the set each element in 'other'
    default void removeAll(IntSet other) {
      for (Integer i : other) {
        remove(i);
      }
    }

    // Return true iff the set contains every element of 'other'
    default boolean contains(IntSet other) {
      boolean conts = true;

      for (Integer i : other) {
        conts = contains(i);

        if (!conts) {
          break;
        }
      }

      return conts;
    }
  }

  // Added for a6e7
  public abstract static class AbstractIntSet implements IntSet {

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("[");

      for (Integer i : this) {
        if (sb.length() > 1) {
          sb.append(", ");
        }

        sb.append(i);
      }

      sb.append("]");

      return sb.toString();
    }
  }

  public static class MemoryEfficientSet extends AbstractIntSet {

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

    // Added for a6e7
    @Override
    public IntSetIterator iterator() {
      return new IntSetIterator() {

        final Iterator<Integer> backingIterator = backingSet.iterator();

        @Override
        public boolean hasNext() {
          return backingIterator.hasNext();
        }

        @Override
        public Integer next() {
          return backingIterator.next();
        }
      };
    }
  }

  public static class SpeedEfficientSet extends AbstractIntSet {

    private final Map<Integer, Boolean> backingMap = new HashMap<>();

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

    // Added for a6e7
    @Override
    public IntSetIterator iterator() {
      return new IntSetIterator() {

        final Iterator<Integer> backingIterator = backingMap.keySet().iterator();

        @Override
        public boolean hasNext() {
          return backingIterator.hasNext();
        }

        @Override
        public Integer next() {
          return backingIterator.next();
        }
      };
    }
  }
}
