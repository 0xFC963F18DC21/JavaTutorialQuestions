package net.nergi.solutions;

import static net.nergi.Utils.arrayListOf;

import java.util.ArrayList;
import java.util.stream.Stream;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class Pd3f5 implements Solution {

  public static Stream<Integer> restrictToPositiveIntegers(Stream<Number> numbers) {
    return numbers.filter((n) -> n instanceof Integer && n.intValue() >= 0).map(Number::intValue);
  }

  public static Stream<Integer> restrictToPositiveIntegersWildcard(
      Stream<? extends Number> numbers) {
    return numbers.filter((n) -> n instanceof Integer && n.intValue() >= 0).map(Number::intValue);
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "d3f5: Streams and downcasting";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    final ArrayList<Integer> listInts = arrayListOf(0, -1, 2, -3);
    final ArrayList<Short> listShorts = arrayListOf((short) 0, (short) 5, (short) -10, (short) -50);
    final ArrayList<Long> listLongs = arrayListOf(-1L, -10L, 100L, 1000L);
    final ArrayList<Byte> listBytes = arrayListOf((byte) -8, (byte) 4, (byte) -2, (byte) 1);
    final ArrayList<Float> listFloats = arrayListOf(-0.5f, 3.14f, 2.718f);
    final ArrayList<Double> listDoubles = arrayListOf(-5.5, -10.4, 420.69);

    final ArrayList<Number> listAll = new ArrayList<>();
    listAll.addAll(listInts);
    listAll.addAll(listShorts);
    listAll.addAll(listLongs);
    listAll.addAll(listBytes);
    listAll.addAll(listFloats);
    listAll.addAll(listDoubles);

    // This works:
    restrictToPositiveIntegers(listAll.stream()).forEach(System.out::println);

    // This doesn't:
    // -- restrictToPositiveIntegers(listInts.stream()).forEach(System.out::println); --

    // This works again:
    restrictToPositiveIntegersWildcard(listInts.stream()).forEach(System.out::println);
  }
}
