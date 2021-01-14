package net.nergi;

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
}
