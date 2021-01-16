package net.nergi.solutions;

import net.nergi.Solution;

@SuppressWarnings("unused")
public class Pbdb4 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return null;
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    System.out.println(
        "There is no output expected from this solution. See the source code for details."
    );
  }

  public static class Rectangle {

    // Add final modifiers as such:
    private /* final */ int width;
    private /* final */ int height;

    public Rectangle(int width, int height) {
      // Remove this:
      // setWidth(width);
      // setHeight(height);

      // Replace with:
      this.width = width;
      this.height = height;
    }

    // Remove this code...
    @Deprecated
    public void setWidth(int width) {
      this.width = width;
    }

    @Deprecated
    public void setHeight(int height) {
      this.height = height;
    }
    // ... to here

    public int getWidth() {
      return width;
    }

    public int getHeight() {
      return height;
    }

  }

  public static class House {

    private Rectangle floorSize;
    private int floorArea;

    public House(Rectangle floorSize) {
      setFloorSize(floorSize);
    }

    public Rectangle getFloorSize() {
      /* Exposing the rectangle for floor size is dangerous as it exposes the ability to
       * set the width and height of the internal rectangle without actually changing the
       * area of the floor.
       *
       * Potential solution:
       * - Make Rectangle an immutable class.
       * - Return a new rectangle instead of a direct reference to the internal rectangle.
       */

      // Replace this:
      // return floorSize;

      // With this:
      return new Rectangle(floorSize.getWidth(), floorSize.getHeight());
    }

    public void setFloorSize(Rectangle floorSize) {
      this.floorSize = floorSize;
      floorArea = floorSize.getWidth() * floorSize.getHeight();
    }

    public int getFloorArea() {
      return floorArea;
    }

  }
}
