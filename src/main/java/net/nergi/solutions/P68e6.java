package net.nergi.solutions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.nergi.Solution;

/** Solution for 68e6. */
@SuppressWarnings("unused")
public class P68e6 implements Solution {

  private static void testEquality(Object expected, Object actual) throws RuntimeException {
    if (!expected.equals(actual)) {
      throw new RuntimeException(String.format("\nExpected: %s\n  Actual: %s", expected, actual));
    }

    System.out.println("Received expected: " + expected);
  }

  public static List<Integer> concatenate(List<List<Integer>> lists) {
    return lists.stream()
        .reduce(
            List.of(),
            (acc, it) -> {
              ArrayList<Integer> res = new ArrayList<>(acc);
              res.addAll(it);
              return res;
            });
  }

  public static int findMin(List<Integer> numbers) {
    return numbers.stream().reduce(Integer.MAX_VALUE, Math::min);
  }

  /* --------------------------------------------
   * Example class is substituted for this class.
   * --------------------------------------------
   */

  public static int findMinOrZero(List<Integer> numbers) {
    return numbers.stream().reduce(Math::min).orElse(0);
  }

  @SuppressWarnings("Convert2MethodRef")
  public static int findMax(List<Integer> numbers) {
    // Warning suppression is to get IntelliJ to shut up about replacing the lambda with
    // a method reference. This is required because the question specifically asks for a
    // lambda, and not a method reference.
    return numbers.stream().reduce(Integer.MIN_VALUE, (a, b) -> Math.max(a, b));
  }

  @SuppressWarnings("Convert2MethodRef")
  public static int findMaxOrZero(List<Integer> numbers) {
    // See above method for relevant comment on warning suppression.
    return numbers.stream().reduce((a, b) -> Math.max(a, b)).orElse(0);
  }

  public static int findMinOfMaxes(List<List<Integer>> listOfLists) {
    return findMin(listOfLists.stream().map(P68e6::findMax).collect(Collectors.toList()));
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "68e6: Using Stream.reduce";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // main method substituted for this method.
    final List<Integer> list1 = List.of(1, 2, 3, 4, 5, 9);
    final List<Integer> list2 = List.of(1, 10, 100, 1000, 10000);
    final List<Integer> list3 = List.of(6, 7, 8);
    final List<List<Integer>> listOfLists = List.of(list1, list2, list3);

    final List<Integer> allIntegers = concatenate(listOfLists);
    final int maxList1 = findMax(list1);
    final int minList2 = findMin(list2);
    final int maxEmpty = findMax(Collections.emptyList());
    final int minEmpty = findMin(Collections.emptyList());
    final int maxOrZeroEmpty = findMinOrZero(Collections.emptyList());
    final int minOrZeroEmpty = findMaxOrZero(Collections.emptyList());
    final int minOfMaxes = findMinOfMaxes(listOfLists);
    final int minOfMaxesEmpty = findMinOfMaxes(Collections.emptyList());
    final int minOfMaxesListOfEmptyLists =
        findMinOfMaxes(List.of(Collections.emptyList(), Collections.emptyList()));

    // Test results
    try {
      testEquality(List.of(1, 2, 3, 4, 5, 9, 1, 10, 100, 1000, 10000, 6, 7, 8), allIntegers);
      testEquality(9, maxList1);
      testEquality(1, minList2);
      testEquality(Integer.MIN_VALUE, maxEmpty);
      testEquality(Integer.MAX_VALUE, minEmpty);
      testEquality(0, maxOrZeroEmpty);
      testEquality(0, minOrZeroEmpty);
      testEquality(8, minOfMaxes);
      testEquality(Integer.MAX_VALUE, minOfMaxesEmpty);
      testEquality(Integer.MIN_VALUE, minOfMaxesListOfEmptyLists);
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
  }
}
