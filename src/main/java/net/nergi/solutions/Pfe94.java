package net.nergi.solutions;

import java.util.List;
import java.util.stream.Collectors;
import net.nergi.Solution;

/** Solution for fe94. */
@SuppressWarnings("unused")
public class Pfe94 implements Solution {

  public static List<String> reverseEachString(List<String> input) {
    return input.stream()
        .map(StringBuilder::new)
        .map(StringBuilder::reverse)
        .map(StringBuilder::toString)
        .collect(Collectors.toList());
  }

  public static List<String> reverseEachStringMonolithic(List<String> input) {
    return input.stream()
        .map((s) -> new StringBuilder(s).reverse().toString())
        .collect(Collectors.toList());
  }

  /* --------------------------------------------
   * Example class is substituted for this class.
   * --------------------------------------------
   */

  public static List<Double> sqrtsOfFirstDigits(List<String> input) {
    return input.stream()
        .filter((s) -> Character.isDigit(s.charAt(0)))
        .map((s) -> s.substring(0, 1))
        .map(Double::parseDouble)
        .map(Math::sqrt)
        .collect(Collectors.toList());
  }

  public static List<Double> sqrtsOfFirstDigitsMonolithic(List<String> input) {
    return input.stream()
        .filter((s) -> Character.isDigit(s.charAt(0)))
        .map((s) -> Math.sqrt(Double.parseDouble(s.substring(0, 1))))
        .collect(Collectors.toList());
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "fe94: Using Stream.map and Stream.filter";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // main method is substituted for this method.
    List<String> testStrings = List.of("olleh", "dlrow");

    System.out.println(reverseEachString(testStrings));
    System.out.println(reverseEachStringMonolithic(testStrings));

    List<String> moreStrings = List.of("this is crazy", "4pp", "seven-27");

    System.out.println(sqrtsOfFirstDigits(moreStrings));
    System.out.println(sqrtsOfFirstDigitsMonolithic(moreStrings));
  }
}
