package net.nergi.solutions;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import net.nergi.Solution;

/** Solution for 1ae9. */
@SuppressWarnings("unused")
public class P1ae9 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "1ae9: Reusing immutable value objects";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    final ArrayList<Point> points1 = new ArrayList<>();
    final ArrayList<Point> points2 = new ArrayList<>();

    // Make points with factory method:
    for (int i = 0; i < 10; ++i) {
      points1.add(Point.makePoint(1, 2, 3));
    }

    // Make points with old constructor:
    try {
      final Constructor<Point> ctor =
          Point.class.getDeclaredConstructor(int.class, int.class, int.class);

      for (int i = 0; i < 10; ++i) {
        points2.add(ctor.newInstance(1, 2, 3));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Print points
    System.out.println(points1.stream().map(Point::toSuperString).collect(Collectors.toList()));

    System.out.println(points2.stream().map(Point::toSuperString).collect(Collectors.toList()));
  }

  public static class Point {

    // Changed for 290b
    private static final Map<Point, WeakReference<Point>> pool = new WeakHashMap<>();

    private final int coordX;
    private final int coordY;
    private final int coordZ;

    private Point(int coordX, int coordY, int coordZ) {
      this.coordX = coordX;
      this.coordY = coordY;
      this.coordZ = coordZ;
    }

    /**
     * Factory method for Point that reuses refrences for identical points with the same
     * coordinates.
     *
     * <p>Also changed for 290b.
     */
    public static Point makePoint(int x, int y, int z) {
      final Point p = new Point(x, y, z);
      final WeakReference<Point> inPool = pool.get(p);

      if (inPool == null) {
        pool.put(p, new WeakReference<>(p));
        return p;
      } else {
        return inPool.get();
      }
    }

    public String toSuperString() {
      return String.format("%x", System.identityHashCode(this));
    }

    @Override
    public String toString() {
      return "(" + coordX + ", " + coordY + ", " + coordZ + ")";
    }

    @Override
    public boolean equals(Object that) {
      return that instanceof Point
          && coordX == ((Point) that).coordX
          && coordY == ((Point) that).coordY
          && coordZ == ((Point) that).coordZ;
    }

    @Override
    public int hashCode() {
      return Integer.valueOf(coordX).hashCode()
          ^ Integer.valueOf(coordY).hashCode()
          ^ Integer.valueOf(coordZ).hashCode();
    }
  }
}
