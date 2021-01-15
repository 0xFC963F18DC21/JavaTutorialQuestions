package net.nergi.solutions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.nergi.Solution;
import net.nergi.Utils;

@SuppressWarnings("unused")
public class Pf7c3 implements Solution {

  public static final List<Character> vowels = Utils.listOf('a', 'e', 'i', 'o', 'u');

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "f7c3: Pig Latin";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    ArrayList<String> strings = new ArrayList<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line;

    // Get one line of input
    System.out.println("Accepting input:");
    do {
      try {
        line = br.readLine();

        if (line != null) {
          strings.add(line);
        } else {
          br.close();
        }
      } catch (IOException e) {
        // Exit the program, something went wrong.
        e.printStackTrace();
        return;
      }
    } while (line != null);

    // Processing
    System.out.println("\nOutputs:");
    for (String str : strings) {
      var partitions = partitionString(str).stream().filter((s) -> s.length() > 0);
      var results = partitions.map(Pf7c3::pigLatinise);
      System.out.println(results.collect(Collectors.joining()));
    }
  }

  private static ArrayList<String> partitionString(String str) {
    final ArrayList<String> words = new ArrayList<>();
    final StringBuilder sb = new StringBuilder();

    for (char c : str.toCharArray()) {
      if (Character.isLetterOrDigit(c)) {
        sb.append(c);
      } else {
        words.add(sb.toString());
        words.add(c + "");
        sb.setLength(0); // Clear StringBuilder
      }
    }

    if (sb.length() > 0) {
      words.add(sb.toString());
    }

    return words;
  }

  public static String pigLatinise(String word) {
    if (word.length() <= 0) {
      return "";
    }

    if (word.length() == 1 && Character.isLetter(word.charAt(0))) {
      return vowels.contains(Character.toLowerCase(word.charAt(0))) ? word : word + "ay";
    } else if (word.length() == 1) {
      return word;
    }

    if (word.chars().anyMatch(Character::isDigit)) {
      return word;
    }

    boolean shouldCap = Character.isUpperCase(word.charAt(0));

    char begin = Character.toLowerCase(word.charAt(0));

    if (!vowels.contains(begin)) {
      StringBuilder sb = new StringBuilder(word.substring(1));
      char newStart = sb.charAt(0);

      sb.insert(1, shouldCap ? Character.toUpperCase(newStart) : newStart);

      sb.append(begin);
      sb.append("ay");

      return sb.substring(1);
    } else {
      return word + "way";
    }
  }
}
