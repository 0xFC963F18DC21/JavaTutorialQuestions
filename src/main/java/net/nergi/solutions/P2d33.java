package net.nergi.solutions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P2d33 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "2d33: Reversed order of input";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    Stack<String> lines = new Stack<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line;

    System.out.println("Accepting input:");
    do {
      try {
        line = br.readLine();

        if (line != null) {
          lines.add(line);
        } else {
          br.close();
        }
      } catch (IOException e) {
        // Exit the program, something went wrong.
        e.printStackTrace();
        return;
      }
    } while (line != null);

    System.out.println("Printing in reverse order:");
    while (!lines.isEmpty()) {
      System.out.println(lines.pop());
    }
  }
}
