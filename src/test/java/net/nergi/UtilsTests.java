package net.nergi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

public class UtilsTests {

  @Test
  @SuppressWarnings("deprecation")
  public void listOfTests() {
    /* Suppressing annotation for deprecation warnings required as this unit test was used to test
     * the (now deprecated) Utils.listOf() method.
     */
    assertEquals(Arrays.asList(1, 2, 3, 4, 5), Utils.listOf(1, 2, 3, 4, 5));
    assertNotEquals(Arrays.asList(1f, 2f, 3f, 4f, 5f), Utils.listOf(2f, 2f, 3f, 4f, 5f));
  }

  @Test
  public void arrayListOfTests() {
    var tArray = Arrays.asList(1, 2, 3, 4, 5);
    var diffTArray = Arrays.asList(5, 4, 3, 2, 1);

    assertEquals(new ArrayList<>(tArray), Utils.mutableListOf(1, 2, 3, 4, 5));
    assertNotEquals(new ArrayList<>(diffTArray), Utils.mutableListOf(1, 2, 3, 4, 5));
  }

  @Test
  public void hashMapOfTests() {
    Integer[] arr1 = new Integer[] {1, 2, 3, 4, 5};
    String[] arr2 = new String[] {"a", "b", "c", "d", "e"};
    String[] arr3 = new String[] {"e", "d", "c", "b", "a"};
    Integer[] arr4 = new Integer[] {1, 2, 3, 4};

    HashMap<Integer, String> correctMap = new HashMap<>();
    for (int i = 0; i < arr1.length; ++i) {
      correctMap.put(arr1[i], arr2[i]);
    }

    assertEquals(correctMap, Utils.mapOf(arr1, arr2));
    assertNotEquals(correctMap, Utils.mapOf(arr1, arr3));
    assertThrows(IllegalArgumentException.class, () -> Utils.mapOf(arr4, arr2));

    List<Integer> l1 = List.of(1, 2, 3, 4, 5);
    List<Integer> l2 = List.of(1, 2, 3, 4);
    List<String> l3 = List.of("a", "b", "c", "d", "e");
    List<String> l4 = List.of("e", "d", "c", "b", "a");

    assertEquals(correctMap, Utils.mapOf(l1, l3));
    assertNotEquals(correctMap, Utils.mapOf(l1, l4));
    assertThrows(IllegalArgumentException.class, () -> Utils.mapOf(l2, l3));
  }
}
