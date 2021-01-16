package net.nergi.solutions;

import java.util.HashSet;
import java.util.Set;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class Pc2b8 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "c2b8: Irresponsible rectangle";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    DrawingEngineDemo.main(new String[0]);
  }

  /* -------------------------------------------------------------
   * For more details on the fixes, look at Pc2b8-Refactorings.txt
   * -------------------------------------------------------------
   */

  public static class Point {

    public final int coordX;
    public final int coordY;

    public Point(int coordX, int coordY) {
      this.coordX = coordX;
      this.coordY = coordY;
    }

    @Override
    public int hashCode() {
      return coordX + coordY * 31;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Point) {
        Point other = (Point) obj;
        return coordX == other.coordX
            && coordY == other.coordY;
      } else {
        return false;
      }
    }

    @Override
    public String toString() {
      return String.format("(%d, %d)", coordX, coordY);
    }

  }

  public static class Rectangle {

    public final Point topLeft;
    public final int width;
    public final int height;

    public Rectangle(Point topLeft, int width, int height) {
      this.topLeft = topLeft;
      this.width = width;
      this.height = height;
    }

    public int area() {
      return width * height;
    }

    public Point getBottomRight() {
      return new Point(topLeft.coordX + width, topLeft.coordY + height);
    }

    public boolean contains(Rectangle other) {
      return topLeft.coordX <= other.topLeft.coordX
          && topLeft.coordY <= other.topLeft.coordY
          && this.getBottomRight().coordX >= other.getBottomRight().coordX
          && this.getBottomRight().coordY >= other.getBottomRight().coordY;
    }

    @Override
    public int hashCode() {
      return topLeft.hashCode() + width * 31 + (height * 31 * 31);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Rectangle) {
        Rectangle other = (Rectangle) obj;
        return topLeft.equals(other.topLeft)
            && width == other.width
            && height == other.height;
      } else {
        return false;
      }
    }

    @Override
    public String toString() {
      return String.format("(top-left = %s, width = %d, height = %d)", topLeft, width, height);
    }

  }

  public static class DrawingEngine {

    private final Set<Rectangle> rectangles;

    public DrawingEngine() {
      rectangles = new HashSet<>();
    }

    public void addRectangle(Rectangle rectangle) {
      rectangles.add(rectangle);
    }

    public int maxArea() {
      int result = 0;
      for (Rectangle r : rectangles) {
        if (r.area() > result) {
          result = r.area();
        }
      }
      return result;
    }

    public String toString() {

      final StringBuilder result = new StringBuilder(
          "Drawing engine is looking after these rectangles:"
      );
      for (Rectangle r : rectangles) {
        result.append("\n   ").append(r.toString());
      }
      return result.toString();

    }

  }

  public static class DrawingEngineDemo {

    public static void main(String[] args) {

      DrawingEngine engine = new DrawingEngine();

      engine.addRectangle(new Rectangle(new Point(0, 0), 10, 20));
      engine.addRectangle(new Rectangle(new Point(2, 2), 100, 1));
      engine.addRectangle(new Rectangle(new Point(50, 10), 22, 22));

      System.out.println(engine);

      System.out.println("Max area of these rectangles is "
          + engine.maxArea());

      Rectangle r1 = new Rectangle(new Point(5, 5), 10, 20);
      Rectangle r2 = new Rectangle(new Point(2, 2), 15, 25);

      System.out.println("Rectangle " + r2.toString() + " contains "
          + r1.toString() + ": " + r2.contains(r1));

    }

  }
}
