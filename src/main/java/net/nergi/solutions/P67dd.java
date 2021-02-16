package net.nergi.solutions;

import static net.nergi.Utils.mutableListOf;
import static net.nergi.Utils.getUserLines;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.nergi.Solution;

/** Solution for 67dd. */
@SuppressWarnings("unused")
public class P67dd implements Solution {

  /**
   * Gets the words from a string. A word in this case is defined as an alphanumeric string which
   * contains no symbols.
   *
   * @param str String to split into words
   * @return A list of words in the string
   */
  public static List<String> getWords(String str) {
    final StringBuilder sb = new StringBuilder();
    final List<String> words = mutableListOf();

    for (char c : str.toCharArray()) {
      if (Character.isLetterOrDigit(c)) {
        sb.append(c);
      } else {
        if (sb.length() > 0) {
          words.add(sb.toString());
          sb.setLength(0);
        }
      }
    }

    if (sb.length() > 0) {
      words.add(sb.toString());
    }

    return words;
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "67dd: Word count";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    System.out.println("Getting input:");
    final ArrayList<String> lines = getUserLines();
    if (lines == null) {
      System.out.println("Error in getting lines. Program closing.");
      return;
    }

    Supplier<Stream<List<String>>> wordsInLines = () -> lines.stream().map(P67dd::getWords);

    // Print statistics
    System.out.println("Lines: " + lines.size());
    System.out.println("Words: " + wordsInLines.get().mapToInt(List::size).sum());
    System.out.println(
        "Characters: "
            + wordsInLines.get().mapToInt((a) -> a.stream().mapToInt(String::length).sum()).sum());
  }
}
