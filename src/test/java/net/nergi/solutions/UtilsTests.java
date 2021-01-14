package net.nergi.solutions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.nergi.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class UtilsTests {

  @Test
  public void listOfTests() {
    assertEquals(Arrays.asList(1, 2, 3, 4, 5), Utils.listOf(1, 2, 3, 4, 5));
    assertNotEquals(Arrays.asList(1f, 2f, 3f, 4f, 5f), Utils.listOf(2f, 2f, 3f, 4f, 5f));
  }

  @Test
  public void arrayListOfTests() {
    var tArray = Arrays.asList(1, 2, 3, 4, 5);
    var diffTArray = Arrays.asList(5, 4, 3, 2, 1);

    assertEquals(new ArrayList<>(tArray), Utils.arrayListOf(1, 2, 3, 4, 5));
    assertNotEquals(new ArrayList<>(diffTArray), Utils.arrayListOf(1, 2, 3, 4, 5));
  }

  @Test
  public void hashMapOfTests() {
    Integer[] arr1 = new Integer[] { 1, 2, 3, 4, 5 };
    String[] arr2 = new String[] { "a", "b", "c", "d", "e" };
    String[] arr3 = new String[] { "e", "d", "c", "b", "a" };
    Integer[] arr4 = new Integer[] { 1, 2, 3, 4 };

    HashMap<Integer, String> correctMap = new HashMap<>();
    for (int i = 0; i < arr1.length; ++i) {
      correctMap.put(arr1[i], arr2[i]);
    }

    assertEquals(correctMap, Utils.hashMapOf(arr1, arr2));
    assertNotEquals(correctMap, Utils.hashMapOf(arr1, arr3));
    assertThrows(IllegalArgumentException.class, () -> Utils.hashMapOf(arr4, arr2));
  }
}
