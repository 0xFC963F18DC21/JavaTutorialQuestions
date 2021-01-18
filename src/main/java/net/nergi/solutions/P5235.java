package net.nergi.solutions;

import net.nergi.Solution;

@SuppressWarnings("unused")
public class P5235 implements Solution {

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
    final P0f05.Point p1 = new P0f05.Point(0, 0, 0);
    final P0f05.Point p2 = new P0f05.Point(0, 0, 0);
    final P0f05.Point p3 = new P0f05.Point(1, 2, 3);

    final P0f05.ColouredPoint cp1 = new P0f05.ColouredPoint(0, 0, 0, 255, 0, 0);
    final P0f05.ColouredPoint cp2 = new P0f05.ColouredPoint(0, 0, 0, 255, 0, 0);
    final P0f05.ColouredPoint cp3 = new P0f05.ColouredPoint(1, 2, 3, 0, 0, 255);

    System.out.println(p1.equals(p2));
    System.out.println(p1.equals(p3));

    System.out.println(cp1.equals(cp2));
    System.out.println(cp1.equals(cp3));

    System.out.println(p1.equals(cp1));
    System.out.println(p1.equals(cp2));
    System.out.println(p1.equals(cp3));

    System.out.println(cp1.equals(p1));
    System.out.println(cp2.equals(p1));
    System.out.println(cp3.equals(p1));
  }

}
