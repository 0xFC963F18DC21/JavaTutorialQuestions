package net.nergi.solutions;

import java.math.BigInteger;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class Pe6fd implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "e6fd: Bit sets";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    // Demo class is substituted by this method.
    BitSet8 bits8 = new BitSet8();
    bits8.add(2);
    bits8.add(3);
    bits8.add(5);

    BitSet64 bits64 = new BitSet64();
    bits64.add(1);
    bits64.add(5);
    bits64.add(9);

    bits8.intersect(bits64);

    System.out.println(bits8.contains(2));
    System.out.println(bits8.contains(3));
    System.out.println(bits8.contains(5));

    // See SolutionTests.java for further testing on the other classes.
    System.out.println("Check SolutionTests.java for more details.");
  }

  /**
   * An interface that represents collections that represent a set of integers from 0 to some upper
   * bound. Instead of being backed by another collection, this set is backed by the bitwise
   * representation of whether a number is in or not in the set.
   */
  public interface BitSet {

    /**
     * Adds a number to the bit set.
     *
     * @param x Number to add to set
     * @throws IllegalArgumentException This is thrown if the number is out of range.
     */
    void add(int x) throws IllegalArgumentException;

    /**
     * Removes a number from the bit set.
     *
     * @param x Number to remove from set
     */
    void remove(int x);

    /**
     * Checks if a number is in the set.
     *
     * @param x Number to query
     * @return <code>true</code> if the number is in the set, <code>false</code> otherwise.
     */
    boolean contains(int x);

    /**
     * Mutes the bit set to only contain the values contained in both it and <code>s</code>.
     *
     * @param s Set to intersect with
     */
    void intersect(BitSet s);

    /**
     * Returns the maximum storable value in this bit set.
     */
    int maxStorableValue();

    /**
     * Returns the bit representation of this bit set.
     */
    Number getRepresentation();

    default boolean isValid(int x) {
      return 0 <= x && x <= maxStorableValue();
    }

  }

  public static class BitSet8 implements BitSet {

    private byte backingByte = 0;

    @Override
    public void add(int x) throws IllegalArgumentException {
      if (isValid(x)) {
        backingByte |= (1 << x);
      } else {
        throw new IllegalArgumentException(String
            .format("%d is is out of range! Accepted interval is [0, %d]", x, maxStorableValue()));
      }
    }

    @Override
    public void remove(int x) {
      backingByte &= ~(1 << x);
    }

    @Override
    public boolean contains(int x) {
      return (backingByte & (1 << x)) > 0;
    }

    @Override
    public void intersect(BitSet s) {
      if (s instanceof BitSet8) {
        backingByte &= (Byte) s.getRepresentation();
      } else {
        for (int i = 0; i < 8; ++i) {
          if (contains(i) && s.contains(i)) {
            add(i);
          } else {
            remove(i);
          }
        }
      }
    }

    @Override
    public int maxStorableValue() {
      return 7;
    }

    @Override
    public Number getRepresentation() {
      return backingByte;
    }

  }

  public static class BitSet32 implements BitSet {

    private int backingInt = 0;

    @Override
    public void add(int x) throws IllegalArgumentException {
      if (isValid(x)) {
        backingInt |= (1 << x);
      } else {
        throw new IllegalArgumentException(String
            .format("%d is is out of range! Accepted interval is [0, %d]", x, maxStorableValue()));
      }
    }

    @Override
    public void remove(int x) {
      backingInt &= ~(1 << x);
    }

    @Override
    public boolean contains(int x) {
      return (backingInt & (1 << x)) > 0;
    }

    @Override
    public void intersect(BitSet s) {
      if (s instanceof BitSet32) {
        backingInt &= (Integer) s.getRepresentation();
      } else {
        for (int i = 0; i < 32; ++i) {
          if (contains(i) && s.contains(i)) {
            add(i);
          } else {
            remove(i);
          }
        }
      }
    }

    @Override
    public int maxStorableValue() {
      return 31;
    }

    @Override
    public Number getRepresentation() {
      return backingInt;
    }

  }

  public static class BitSet64 implements BitSet {

    private long backingLong = 0L;

    @Override
    public void add(int x) throws IllegalArgumentException {
      if (isValid(x)) {
        backingLong |= (1L << x);
      } else {
        throw new IllegalArgumentException(String
            .format("%d is is out of range! Accepted interval is [0, %d]", x, maxStorableValue()));
      }
    }

    @Override
    public void remove(int x) {
      backingLong &= ~(1L << x);
    }

    @Override
    public boolean contains(int x) {
      return (backingLong & (1L << x)) > 0;
    }

    @Override
    public void intersect(BitSet s) {
      if (s instanceof BitSet32) {
        backingLong &= (Integer) s.getRepresentation();
      } else {
        for (int i = 0; i < 64; ++i) {
          if (contains(i) && s.contains(i)) {
            add(i);
          } else {
            remove(i);
          }
        }
      }
    }

    @Override
    public int maxStorableValue() {
      return 63;
    }

    @Override
    public Number getRepresentation() {
      return backingLong;
    }

  }

  public static class BitSetArray implements BitSet {

    private final int maxValue;
    private final long[] backingLongArray;

    public BitSetArray(int maxValue) {
      this.maxValue = maxValue;
      backingLongArray = new long[(int) (Math.log10(maxValue) / Math.log10(2)) + 1];
    }

    /**
     * Returns a two-item array that represents (x / 64, x % 64)
     */
    private int[] quotRem(int x) {
      int[] places = new int[2];
      places[0] = x / 64;
      places[1] = x % 64;

      return places;
    }

    @Override
    public void add(int x) throws IllegalArgumentException {
      if (isValid(x)) {
        int[] place = quotRem(x);
        backingLongArray[place[0]] |= (1L << place[1]);
      } else {
        throw new IllegalArgumentException(String
            .format("%d is is out of range! Accepted interval is [0, %d]", x, maxStorableValue()));
      }
    }

    @Override
    public void remove(int x) {
      int[] place = quotRem(x);
      if (place[0] >= backingLongArray.length || x > maxStorableValue()) {
        return;
      }

      backingLongArray[place[0]] &= ~(1L << place[1]);
    }

    @Override
    public boolean contains(int x) {
      int[] place = quotRem(x);
      if (place[0] >= backingLongArray.length || x > maxStorableValue()) {
        return false;
      }

      return (backingLongArray[place[0]] & (1L << place[1])) > 0;
    }

    @Override
    public void intersect(BitSet s) {
      for (int x = 0; x <= maxStorableValue(); ++x) {
        if (contains(x) && s.contains(x)) {
          add(x);
        } else {
          remove(x);
        }
      }
    }

    @Override
    public int maxStorableValue() {
      return maxValue;
    }

    @Override
    public Number getRepresentation() {
      BigInteger bi = new BigInteger("0");
      for (int x = 0; x <= maxStorableValue(); ++x) {
        if (contains(x)) {
          bi = bi.or(new BigInteger("1").shiftLeft(x));
        }
      }

      return bi;
    }

  }
}
