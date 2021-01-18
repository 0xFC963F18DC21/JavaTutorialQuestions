package net.nergi.solutions;

import java.util.Objects;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P0f05 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "0f05: Coloured points";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    Point noColour = new Point(0, 3, 4);
    ColouredPoint withColour = new ColouredPoint(0, -3, -4, 255, 128, 0);

    System.out.println(noColour);
    System.out.println(withColour);

    System.out.println(Point.getOrigin());

    System.out.println(noColour.distance(withColour));
    System.out.println(withColour.distance(noColour));

    System.out.println(noColour.magnitude());
    System.out.println(withColour.magnitude());
  }

  public static class Point {

    private static final Point origin = new Point(0, 0, 0);

    public final double x;
    public final double y;
    public final double z;

    public static Point getOrigin() {
      return origin;
    }

    public Point(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    public double distance(Point other) {
      double dx = x - other.x;
      double dy = y - other.y;
      double dz = z - other.z;

      return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double magnitude() {
      return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public String toString() {
      return String.format("(%f, %f, %f)", x, y, z);
    }

    // Extension for 5235

    @Override
    public int hashCode() {
      return Objects.hash(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Point) {
        final Point p = (Point) o;
        return p.x == x && p.y == y && p.z == z;
      } else {
        return false;
      }
    }
  }

  public static class ColouredPoint extends Point {

    public final int red;
    public final int green;
    public final int blue;

    public ColouredPoint(int x, int y, int z) {
      this(x, y, z, 0, 0, 0);
    }

    public ColouredPoint(int x, int y, int z, int red, int green, int blue) {
      super(x, y, z);

      this.red = red;
      this.green = green;
      this.blue = blue;
    }

    @Override
    public String toString() {
      StringBuilder init = new StringBuilder(super.toString());
      init.deleteCharAt(init.length() - 1);
      init.append(String.format(" | Colour: %d, %d, %d)", red, green, blue));

      return init.toString();
    }

    // Extension for 5235

    @Override
    public int hashCode() {
      return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof ColouredPoint) {
        final ColouredPoint cp = (ColouredPoint) o;
        return super.equals(cp) && cp.red == red && cp.green == green && cp.blue == blue;
      } else {
        return false;
      }
    }
  }

}
