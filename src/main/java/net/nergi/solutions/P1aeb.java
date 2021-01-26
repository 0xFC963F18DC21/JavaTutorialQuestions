package net.nergi.solutions;

import java.util.Set;
import java.util.stream.Collectors;
import net.nergi.Solution;
import net.nergi.Utils;

/** Solution for 1aeb. */
@SuppressWarnings("unused")
public class P1aeb implements Solution {

  public static <T extends Number> Set<T> readNumbers(int numbersToRead, NumberParser<T> parser) {
    System.out.printf(
        "Please enter %d %ss, one line at a time:\n", numbersToRead, parser.typeParsed());

    return Utils.getUserLines(numbersToRead).stream()
        .map(parser::parseNumber)
        .collect(Collectors.toSet());
  }

  public static <T extends Number> T addNumbers(Set<T> numbers, NumberAdder<T> adder) {
    return numbers.stream().reduce(adder.zero(), adder::add);
  }

  public static <T extends Number> void displayAsDoubles(Set<T> numbers) {
    System.out.println(numbers.stream().map(Number::doubleValue).collect(Collectors.toSet()));
  }

  public static <T extends Number> void displayAsInts(Set<T> numbers) {
    System.out.println(numbers.stream().map(Number::intValue).collect(Collectors.toSet()));
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "1aeb: Generic number manipulation";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // Get 5 integers
    final var ints = readNumbers(5, new IntegerParser());

    // Get 5 doubles
    final var doubles = readNumbers(5, new DoubleParser());

    // Print inputs
    System.out.println(ints + " sums to " + addNumbers(ints, new IntegerAdder()));
    System.out.println(doubles + " sums to " + addNumbers(doubles, new DoubleAdder()));

    System.out.println("Ints as doubles:");
    displayAsDoubles(ints);

    System.out.println("Doubles as ints:");
    displayAsInts(doubles);
  }

  public interface NumberParser<T extends Number> {

    // Converts String s into a Number of type T
    T parseNumber(String s) throws NumberFormatException;

    // Return the name of the type to which E corresponds. E.g. if E is an Integer,
    // the method should return "int".
    String typeParsed();
  }

  public interface NumberAdder<T extends Number> {

    // Return a number of type T that represents zero
    T zero();

    // Return the sum of x and y
    T add(T x, T y);
  }

  public static class IntegerParser implements NumberParser<Integer> {

    @Override
    public Integer parseNumber(String s) throws NumberFormatException {
      return Integer.parseInt(s);
    }

    @Override
    public String typeParsed() {
      return "int";
    }
  }

  public static class DoubleParser implements NumberParser<Double> {

    @Override
    public Double parseNumber(String s) throws NumberFormatException {
      return Double.parseDouble(s);
    }

    @Override
    public String typeParsed() {
      return "double";
    }
  }

  public static class IntegerAdder implements NumberAdder<Integer> {

    @Override
    public Integer zero() {
      return 0;
    }

    @Override
    public Integer add(Integer x, Integer y) {
      return x + y;
    }
  }

  public static class DoubleAdder implements NumberAdder<Double> {

    @Override
    public Double zero() {
      return 0.0;
    }

    @Override
    public Double add(Double x, Double y) {
      return x + y;
    }
  }
}
