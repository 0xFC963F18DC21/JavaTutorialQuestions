package net.nergi.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P11e2 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "11e2: Bounded generic methods with streams";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    // Initialise collections
    final HashSet<Integer> s1 = new HashSet<>(Arrays.asList(1, 2, 3, 4));
    final ArrayList<Integer> s2 = new ArrayList<>(Arrays.asList(1, 2));
    final Vector<Integer> s3 = new Vector<>(Arrays.asList(1, 2, 3, 4));
    final LinkedHashSet<Integer> s4 = new LinkedHashSet<>(Arrays.asList(3, 4));
    final List<Collection<Integer>> listOfCollectionsOfIntegers = Arrays.asList(
        s1, s2, s3, s4
    );
    final List<Set<Integer>> listOfSetsOfIntegers = Arrays.asList(s1, s4);

    // Get smallest collection and print
    System.out.println(getSmallestCollection(listOfCollectionsOfIntegers));
    System.out.println(getSmallestCollection(listOfSetsOfIntegers));
  }

  private static <E> Optional<? extends Collection<E>> getSmallestCollection(
      List<? extends Collection<E>> collections
  ) {
    return collections.stream()
        .reduce(
            (l, r) -> (r.size() < l.size()) ? r : l
        );
  }

}
