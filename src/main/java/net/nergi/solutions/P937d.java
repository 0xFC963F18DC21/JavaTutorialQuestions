package net.nergi.solutions;

import net.nergi.Solution;

@SuppressWarnings("unused")
public class P937d implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "937d: Flawed rectangle";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    System.out.println(
        "There is no output expected from this solution. See the source code for details."
    );

    Rectangle rect = new Rectangle(3, 4);

    System.out.println("A 3x4 rectangle has area " + rect.getArea() + ".");

    rect.setWidth(4);

    System.out.println("A 4x4 rectangle has area " + rect.getArea() + ".");
  }

  public static class Rectangle {

    private int width;
    private int height;
    private int area;

    /* Originally, area is not updated when updating the width and height.
     * To fix this, either make everything immutable, or remember to update area if either
     * the width or height is updated.
     *
     * The instance method setArea also makes no sense as the area is dependent on the
     * width and height.
     */

    public Rectangle(int width, int height) {
      this.width = width;
      this.height = height;
      this.area = width * height;
    }

    public int getWidth() {
      return width;
    }

    public void setWidth(int width) {
      this.width = width;

      // NEW CODE
      area = width * height;
    }

    public int getHeight() {
      return height;
    }

    public void setHeight(int height) {
      this.height = height;

      // NEW CODE
      area = width * height;
    }

    public int getArea() {
      return area;
    }

    /* --- Method removed from class ---
     * public void setArea(int area) {
     *   this.area = area;
     * }
     */

  }
}
