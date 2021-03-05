package net.nergi.misc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.ObjIntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.nergi.util.Pair;

/**
 * Demo class for several sorting algorithms. See source code for more details.
 */
@SuppressWarnings("unused")
public class Sorted implements Runnable {

  @Override
  public void run() {
    // Make example list:
    final int maxItem = 100;
    final Random rnd = new Random();
    final List<Integer> rndList = IntStream.range(0, 16)
        .map(i -> rnd.nextInt(maxItem))
        .boxed()
        .collect(Collectors.toCollection(ArrayList::new));

    // Demonstrate
    System.out.println("Initial list:");
    System.out.println(rndList);

    System.out.println("\nWith Java's Timsort:");
    System.out.println(rndList.stream().sorted().collect(Collectors.toList()));

    System.out.println("\nWith bubble sort:");
    System.out.println(bubbleSort(rndList, Comparator.comparingInt(i -> i)));

    System.out.println("\nWith insertion sort:");
    System.out.println(insertionSort(rndList, Comparator.comparingInt(i -> i)));

    System.out.println("\nWith merge sort:");
    System.out.println(mergeSort(rndList, Comparator.comparingInt(i -> i)));
  }

  /**
   * A simple swap-sort which "bubbles" the largest item to the end of the list with each pass.
   * O(n^2).
   *
   * <p><strong>Addendum</strong>:
   * <a href="https://twitter.com/Tegu_Artist/status/1364681511023366155">
   * https://twitter.com/Tegu_Artist/status/1364681511023366155</a>
   *
   * @param list List to sort
   * @param key  Sorting key
   * @param <T>  Type held in list
   * @return A sorted list. Does not modify the old list.
   */
  public static <T> List<T> bubbleSort(List<T> list, Comparator<T> key) {
    final List<T> newList = new ArrayList<>(list);

    final ObjIntConsumer<Integer> swap = (p1, p2) -> {
      final T item = newList.get(p1);
      newList.set(p1, newList.get(p2));
      newList.set(p2, item);
    };

    for (int max = list.size() - 1; max >= 0; --max) {
      boolean flag = false;

      for (int i = 0; i < max; ++i) {
        final int comp = key.compare(newList.get(i), newList.get(i + 1));
        if (comp > 0) {
          swap.accept(i, i + 1);
          flag = true;
        }
      }

      if (!flag) {
        return newList;
      }
    }

    return newList;
  }

  private static <T> void insert(List<T> destination, T item, Comparator<T> key) {
    if (!destination.isEmpty()) {
      for (int i = 0; i < destination.size(); ++i) {
        if (key.compare(item, destination.get(i)) < 0) {
          destination.add(i, item);
          return;
        }
      }
    }

    destination.add(item);
  }

  /**
   * Sort the list by inserting each item in their correct place relative to each other, one at a
   * time. O(n ^ 2).
   *
   * @param list List to sort
   * @param key  Sorting key
   * @param <T>  Type held in list
   * @return A sorted list. Does not modify the original list.
   */
  public static <T> List<T> insertionSort(List<T> list, Comparator<T> key) {
    final List<T> newList = new ArrayList<>();
    list.forEach(i -> insert(newList, i, key));

    return newList;
  }

  // Assumption: both lists are already sorted.
  // *** Will drain both lists. ***
  private static <T> List<T> merge(List<T> list1, List<T> list2, Comparator<T> key) {
    final List<T> result = new LinkedList<>();

    while (!list1.isEmpty() && !list2.isEmpty()) {
      if (key.compare(list1.get(0), list2.get(0)) > 0) {
        result.add(list2.remove(0));
      } else {
        result.add(list1.remove(0));
      }
    }

    final List<T> nonEmpty = list1.isEmpty() ? list2 : list1;
    result.addAll(nonEmpty);

    return result;
  }

  private static <T> Pair<List<T>, List<T>> split(List<T> list) {
    final List<T> list1 = new ArrayList<>();
    final List<T> list2 = new ArrayList<>();
    final int originalSize = list.size();

    for (int i = 0; i < originalSize; i++) {
      if (i < originalSize / 2) {
        list1.add(list.get(i));
      } else {
        list2.add(list.get(i));
      }
    }

    return new Pair<>(list1, list2);
  }

  /**
   * Divide-and-conquer sort algorithm. O(n log n).
   *
   * @param list List to sort
   * @param key  Sorting key
   * @param <T>  Type held in list
   * @return A sorted list. Does not modify the original list.
   */
  public static <T> List<T> mergeSort(List<T> list, Comparator<T> key) {
    if (list.size() <= 1) {
      return list;
    } else {
      final Pair<List<T>, List<T>> splitted = split(list);
      return merge(
          mergeSort(splitted.first, key),
          mergeSort(splitted.second, key),
          key);
    }
  }
}
