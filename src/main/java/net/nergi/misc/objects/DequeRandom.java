package net.nergi.misc.objects;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

/**
 * A strict history-keeping randomiser that refuses to return numbers that exists in its history.
 * Its history length can be set on construction.
 */
@SuppressWarnings("unused")
public class DequeRandom {

  private final Random internalRandom;
  private final Deque<Integer> history = new ArrayDeque<>();
  private final int historyLength;

  /** Create a new {@link DequeRandom} with the given history length. */
  public DequeRandom(int historyLength) {
    this.internalRandom = new Random();
    this.historyLength = historyLength;
  }

  /** Create a new {@link DequeRandom} with the given seed and history length. */
  public DequeRandom(long seed, int historyLength) {
    this.internalRandom = new Random(seed);
    this.historyLength = historyLength;
  }

  /**
   * Enques the given arguments into the history. If giving more arguments than the history's
   * length, the first few arguments are popped off.
   */
  public void setHistory(int... newHistory) {
    for (int i : newHistory) {
      history.add(i);

      if (history.size() > historyLength) {
        history.pop();
      }
    }
  }

  /**
   * Generates the next random number. Never returns a number inside its history.
   *
   * @param upperBound Exclusive upper bound for random number
   * @return The next valid random number
   * @throws IllegalArgumentException Throws if upper bound is &lt;= history size.
   */
  public int nextInt(int upperBound) throws IllegalArgumentException {
    if (upperBound <= historyLength) {
      throw new IllegalArgumentException("Potential inf. loop by upper bound <= history size.");
    }

    int next = internalRandom.nextInt(upperBound);
    while (history.contains(next)) {
      next = internalRandom.nextInt(upperBound);
    }

    history.add(next);

    if (history.size() > historyLength) {
      history.pop();
    }

    return next;
  }

  /** Get history length. Used by external code to verify if they should generate a number. */
  public int getHistoryLength() {
    return historyLength;
  }
}
