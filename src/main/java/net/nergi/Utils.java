package net.nergi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Utility class that holds method shortcuts. */
public final class Utils {

  /** BufferedReader for all methods and for use elsewhere. */
  private static BufferedReader br;

  // Private constructor as there is no need to instantiate this class.
  private Utils() {}

  /** Gets the current BufferedReader instance in this class. Required due to 5d30. */
  public static BufferedReader getBr() {
    if (br == null) {
      br = new BufferedReader(new InputStreamReader(System.in));
    }

    return br;
  }

  /**
   * Constructs an immutable list of items. Depreciated. Use List.of() instead.
   *
   * @param items Items of a certain type
   * @param <T> Type to use in list
   * @return An immutable list of type T
   */
  @SafeVarargs
  @Deprecated(since = "2021/01/16")
  public static <T> List<T> listOf(T... items) {
    return Arrays.asList(items);
  }

  /**
   * Constructs a mutable list of items.
   *
   * @param <T> Type to use in list
   * @param items Items of a certain type
   * @return A mutable ArrayList of type T
   */
  @SafeVarargs
  public static <T> List<T> mutableListOf(T... items) {
    return new ArrayList<>(List.of(items));
  }

  /**
   * Constructs a HashMap from two arrays. The arrays must be of equal length.
   *
   * @param keys The final map's key set
   * @param values The final map's value set
   * @param <X> Type of keys
   * @param <Y> Type of values
   * @return An instance of HashMap with the desired keys and values.
   * @throws IllegalArgumentException If the arrays are not of equal length.
   */
  public static <X, Y> Map<X, Y> mapOf(X[] keys, Y[] values)
      throws IllegalArgumentException {
    if (keys.length != values.length) {
      throw new IllegalArgumentException("The arrays of keys and values are not of equal length.");
    } else {
      HashMap<X, Y> map = new HashMap<>();

      for (int i = 0; i < keys.length; ++i) {
        map.put(keys[i], values[i]);
      }

      return map;
    }
  }

  /**
   * Constructs a HashMap from two Lists. The Lists must be of equal length.
   *
   * @param keys The final map's key set
   * @param values The final map's value set
   * @param <X> Type of keys
   * @param <Y> Type of values
   * @return An instance of HashMap with the desired keys and values.
   * @throws IllegalArgumentException If the Lists are not of equal length.
   */
  public static <X, Y> Map<X, Y> mapOf(List<X> keys, List<Y> values)
      throws IllegalArgumentException {
    if (keys.size() != values.size()) {
      throw new IllegalArgumentException("The arrays of keys and values are not of equal length.");
    } else {
      HashMap<X, Y> map = new HashMap<>();

      for (int i = 0; i < keys.size(); ++i) {
        map.put(keys.get(i), values.get(i));
      }

      return map;
    }
  }

  /**
   * Gets lines of input from the user. Stops collecting lines when EOF is given (via CTRL+Z in
   * Windows or CTRL+D in *nix).
   */
  public static List<String> getUserLines() {
    return getUserLines(-1);
  }

  /**
   * Gets lines of input from the user. Stops collecting lines when EOF is given (via CTRL+Z in
   * Windows or CTRL+D in *nix), or when the number of collected lines exceeds some amount.
   */
  public static List<String> getUserLines(int amount) {
    return getUserLines(amount, true);
  }

  /**
   * Gets lines of input from the user. Stops collecting lines when EOF is given (via CTRL+Z in
   * Windows or CTRL+D in *nix), or when the number of collected lines exceeds some amount. Does not
   * return early on IOException or null input if shouldEarlyReturn is false.
   */
  public static List<String> getUserLines(int amount, boolean shouldEarlyReturn) {
    final ArrayList<String> lines = new ArrayList<>();
    String line;

    do {
      try {
        line = br.readLine();

        if (line != null) {
          lines.add(line);

          if (lines.size() >= amount && amount > 0) {
            break;
          }
        } else {
          if (!shouldEarlyReturn) {
            line = "";
          }
        }
      } catch (IOException e) {
        // Stop taking in input, something went wrong.
        if (shouldEarlyReturn) {
          e.printStackTrace();
          return lines;
        } else {
          System.out.println("Input ignored due to IOException.");
          line = "";
        }
      }
    } while (line != null);

    return lines;
  }

  /**
   * Runs some code and prints the approximate average time taken to complete. This default version
   * runs 100 times and prints the average.
   */
  public static void benchmarkTime(Runnable r) {
    benchmarkTime(r, 100);
  }

  /**
   * Runs some code and prints the approximate average time taken to complete.
   *
   * @param r Runnable code
   * @param amount Amount of runs
   */
  @SuppressWarnings("TextBlockMigration")
  public static void benchmarkTime(Runnable r, int amount) {
    final long startTime = System.nanoTime();

    for (int i = 0; i < amount; ++i) {
      r.run();
    }

    final long endTime = System.nanoTime();

    final double difference = endTime - startTime;
    final double average = difference / amount;

    System.out.printf(
        "Time taken for %d runs: %s ns\n" + "     Average time taken: %s ns\n",
        amount, difference, average);
  }

  /** An empty method used as a sort of "do nothing" method. */
  public static void pass() {
    // This is empty intentionally.
  }

  /** Prints a separator line in the console. */
  public static void printSeparator() {
    System.out.println("-------------------------------------------------------------");
  }
}
