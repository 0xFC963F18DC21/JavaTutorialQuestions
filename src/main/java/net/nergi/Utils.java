package net.nergi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class Utils {

  /**
   * Constructs an immutable list of items. Depreciated. Use List.of() instead.
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
   * Constructs a mutable ArrayList of items.
   * @param items Items of a certain type
   * @param <T> Type to use in list
   * @return A mutable ArrayList of type T
   */
  @SafeVarargs
  public static <T> ArrayList<T> arrayListOf(T... items) {
    return new ArrayList<>(List.of(items));
  }

  /**
   * Constructs a HashMap from two arrays. The arrays must be of equal length.
   * @param keys The final map's key set
   * @param values The final map's value set
   * @param <X> Type of keys
   * @param <Y> Type of values
   * @return An instance of HashMap with the desired keys and values.
   * @throws IllegalArgumentException If the arrays are not of equal length.
   */
  public static <X, Y> HashMap<X, Y> hashMapOf(X[] keys, Y[] values)
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
   * @param keys The final map's key set
   * @param values The final map's value set
   * @param <X> Type of keys
   * @param <Y> Type of values
   * @return An instance of HashMap with the desired keys and values.
   * @throws IllegalArgumentException If the Lists are not of equal length.
   */
  public static <X, Y> HashMap<X, Y> hashMapOf(List<X> keys, List<Y> values)
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
  public static ArrayList<String> getUserLines() {
    ArrayList<String> lines = new ArrayList<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line;

    do {
      try {
        line = br.readLine();

        if (line != null) {
          lines.add(line);
        } else {
          br.close();
        }
      } catch (IOException e) {
        // Stop taking in input, something went wrong.
        e.printStackTrace();
        return lines;
      }
    } while (line != null);

    return lines;
  }
}
