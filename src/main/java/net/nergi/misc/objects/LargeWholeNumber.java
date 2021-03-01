package net.nergi.misc.objects;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A discount, bargain-bin version of {@link BigInteger}. Supports addition and multiplication.
 * Currently does not support negative numbers. Made as an exercise, not to be used except for demo
 * purposes.
 */
@SuppressWarnings("unused")
public class LargeWholeNumber {

  private static final int ARR_SIZE = 128;
  private int[] backingArray;

  /** Make a {@link LargeWholeNumber} from a byte value. */
  public LargeWholeNumber(byte value) {
    this(Byte.toString(value));
  }

  /** Make a {@link LargeWholeNumber} from a short value. */
  public LargeWholeNumber(short value) {
    this(Short.toString(value));
  }

  /** Make a {@link LargeWholeNumber} from an int value. */
  public LargeWholeNumber(int value) {
    this(Integer.toString(value));
  }

  /** Make a {@link LargeWholeNumber} from a long value. */
  public LargeWholeNumber(long value) {
    this(Long.toString(value));
  }

  /**
   * Make a new {@link LargeWholeNumber} from a string representation.
   *
   * @param value String representation of the integer. Must be &gt;= 0.
   */
  public LargeWholeNumber(String value) {
    if (value.startsWith("-")) {
      throw new IllegalArgumentException("This type does not support negative numbers.");
    }

    try {
      backingArray = new int[ARR_SIZE];

      final int[] tmp = value.codePoints()
          .map(i -> Character.digit(i, 10))
          .toArray();

      System.arraycopy(tmp, 0, backingArray, ARR_SIZE - tmp.length, tmp.length);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Not a number", e);
    }
  }

  // Private copy-constructor for these numbers.
  private LargeWholeNumber(int[] value) {
    backingArray = value.clone();
  }

  // Internal method to add a digit to a place.
  private void addDigit(int place, int value) {
    final int result = (backingArray[place] + value) % 10;
    final int carry = (backingArray[place] + value >= 10) ? 1 : 0;

    backingArray[place] = result;
    if (carry != 0) {
      if (place == 0) {
        final int growth = backingArray.length >> 1;
        final int newSize = backingArray.length + growth;
        final int[] newArr = new int[newSize];

        System.arraycopy(backingArray, 0, newArr, growth, backingArray.length);
        backingArray = newArr;
      }

      addDigit(place - 1, carry);
    }
  }

  /**
   * Add another {@link LargeWholeNumber} to this one.
   *
   * @param val {@link LargeWholeNumber} to add.
   */
  public void addValueOf(LargeWholeNumber val) {
    for (int i = ARR_SIZE - 1; i >= 0; --i) {
      addDigit(i, val.backingArray[i]);
    }
  }

  /** Add a byte value to the current {@link LargeWholeNumber}. */
  public void addValueOf(byte val) {
    addValueOf(new LargeWholeNumber(val));
  }

  /** Add a short value to the current {@link LargeWholeNumber}. */
  public void addValueOf(short val) {
    addValueOf(new LargeWholeNumber(val));
  }

  /** Add an int value to the current {@link LargeWholeNumber}. */
  public void addValueOf(int val) {
    addValueOf(new LargeWholeNumber(val));
  }

  /** Add a long value to the current {@link LargeWholeNumber}. */
  public void addValueOf(long val) {
    addValueOf(new LargeWholeNumber(val));
  }

  /**
   * Multiply the current {@link LargeWholeNumber} by a given long value.
   *
   * @param multiplier Multiplier value.
   */
  public void multiplyBy(long multiplier) {
    if (multiplier < 0) {
      throw new IllegalArgumentException("Negative multiplier.");
    }

    if (multiplier == 0) {
      Arrays.fill(backingArray, 0);
    } else if (multiplier > 1) {
      final LargeWholeNumber originalValue = new LargeWholeNumber(backingArray);
      for (long i = 2; i <= multiplier; ++i) {
        addValueOf(originalValue);
      }
    } else {
      throw new IllegalArgumentException("This type does not support negative numbers.");
    }
  }

  @Override
  public String toString() {
    int firstNonZero = ARR_SIZE;
    for (int i = 0; i < ARR_SIZE; ++i) {
      if (backingArray[i] != 0) {
        firstNonZero = i;
        break;
      }
    }

    return Arrays.stream(backingArray)
        .skip(firstNonZero)
        .mapToObj(Integer::toString)
        .collect(Collectors.joining(""));
  }
}
