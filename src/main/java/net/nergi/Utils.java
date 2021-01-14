package net.nergi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class Utils {
  @SafeVarargs
  public static <T> List<T> listOf(T... items) {
    return Arrays.asList(items);
  }

  @SafeVarargs
  public static <T> ArrayList<T> arrayListOf(T... items) {
    return new ArrayList<>(Arrays.asList(items));
  }

  public static <X, Y> HashMap<X, Y> hashMapOf(X[] keys, Y[] values) throws IllegalArgumentException {
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
   * Gets lines of input from the user. Stops collecting lines when EOF is given (via
   * CTRL+Z in Windows or CTRL+D in *nix).
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
        // Exit the program, something went wrong.
        e.printStackTrace();
        return null;
      }
    } while (line != null);

    return lines;
  }
}
