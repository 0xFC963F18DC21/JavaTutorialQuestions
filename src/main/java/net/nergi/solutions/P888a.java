package net.nergi.solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.nergi.Solution;

/** Solution for 888a. */
@SuppressWarnings("unused")
public class P888a implements Solution {

  public static <T> List<T> concatenate(List<List<T>> lists) {
    final ArrayList<T> accumulator = new ArrayList<>();

    for (List<T> list : lists) {
      accumulator.addAll(list);
    }

    return accumulator;
  }

  public static <S, T> List<ImmutablePair<Optional<S>, Optional<T>>> zip(
      List<S> first, List<T> second) {
    final ArrayList<ImmutablePair<Optional<S>, Optional<T>>> result = new ArrayList<>();

    for (int i = 0; i < Math.max(first.size(), second.size()); i++) {
      result.add(
          new ImmutablePair<>(
              i < first.size() ? Optional.of(first.get(i)) : Optional.empty(),
              i < second.size() ? Optional.of(second.get(i)) : Optional.empty()));
    }

    return result;
  }

  public static <S, T> List<ImmutablePair<S, T>> flatten(
      List<ImmutablePair<Optional<S>, Optional<T>>> maybePairs, S defaultS, T defaultT) {
    return maybePairs.stream()
        .map(
            (pair) ->
                new ImmutablePair<>(
                    pair.getFirst().orElse(defaultS), pair.getSecond().orElse(defaultT)))
        .collect(Collectors.toList());
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "888a: Generic methods with streams";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // Test concatenate
    final List<Integer> list1 = List.of(0, 1, 2, 3);
    final List<Integer> list2 = List.of(4, 5, 6, 7, 8, 9);

    System.out.println(concatenate(List.of(list1, list2)));

    // Test zip and flatten
    final var zipped = zip(list1, list2);
    final var flattened = flatten(zipped, -1, -1);

    System.out.println(zipped);
    System.out.println(flattened);
  }

  public static class ImmutablePair<S, T> {

    private final S first;
    private final T second;

    public ImmutablePair(S first, T second) {
      this.first = first;
      this.second = second;
    }

    public S getFirst() {
      return first;
    }

    public T getSecond() {
      return second;
    }

    @Override
    public String toString() {
      return "(" + first + ", " + second + ")";
    }
  }
}
