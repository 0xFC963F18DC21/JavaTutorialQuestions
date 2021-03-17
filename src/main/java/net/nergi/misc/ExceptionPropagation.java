package net.nergi.misc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import net.nergi.util.ExceptionSuppressor;

/**
 * Demonstrations of different ways to propagate exceptions outwards.
 * See the source code for more details.
 */
public class ExceptionPropagation implements Runnable {

  @Override
  public void run() {
    // Catching to continue:
    try {
      checkedPropagation();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    try {
      uncheckedPropagation();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    try {
      checkedStreamPropagationUsingCollection();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    try {
      checkedStreamPropagationUsingSuppressor();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * This method attempts to write to System.out using a buffered reader and simulates an error.
   *
   * @throws IOException If the write fails or the simulation succeeds.
   */
  private static void checkedPropagation() throws IOException {
    final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    bw.write("This is a write from a checked exception method!");
    bw.newLine();
    bw.flush();

    throw new IOException("This is a success!");
  }

  /** An unchecked arithmetic exception. */
  private static void uncheckedPropagation() {
    System.out.println("Entering unchecked exception method.");

    @SuppressWarnings({"divzero", "NumericOverflow"})
    int result = 1 / 0;

    System.out.println(result);
  }

  /**
   * This method attempts to write a few devision results to System.out.
   *
   * @throws IOException If the write fails or the simulation succeeds.
   */
  private static void checkedStreamPropagationUsingCollection() throws IOException {
    System.out.println("Entering checked stream exception catching method [1].");

    final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    final int[] values = { 1, 5, 6, 2, 3, 0, 8, 9, 4, 7 };

    final Queue<IOException> exceptions = new LinkedList<>();

    Arrays.stream(values).forEach(i -> {
      try {
        if (i == 0) {
          throw new IOException("This is a success!");
        }

        bw.write(String.format("%d / %d = %d", 362880, i, 362880 / i));
        bw.newLine();
        bw.flush();
      } catch (IOException e) {
        exceptions.add(e);
      }
    });

    if (!exceptions.isEmpty()) {
      throw exceptions.poll();
    }
  }

  /**
   * This method attempts to write a few devision results to System.out. This version of the method
   * uses an {@link ExceptionSuppressor} in order to temporarily hold onto the exceptions.
   *
   * @throws IOException If the write fails or the simulation succeeds.
   */
  private static void checkedStreamPropagationUsingSuppressor() throws IOException {
    System.out.println("Entering checked stream exception catching method [2].");

    final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    final int[] values = { 1, 5, 6, 2, 3, 0, 8, 9, 4, 7 };

    final ExceptionSuppressor suppressor = new ExceptionSuppressor(IOException.class);

    Arrays.stream(values).forEach(i -> suppressor.suppressedExecute(
        () -> {
          if (i == 0) {
            throw new IOException("This is a success!");
          }

          bw.write(String.format("%d / %d = %d", 362880, i, 362880 / i));
          bw.newLine();
          bw.flush();
        }));

    suppressor.rethrowAny(IOException.class);
  }
}
