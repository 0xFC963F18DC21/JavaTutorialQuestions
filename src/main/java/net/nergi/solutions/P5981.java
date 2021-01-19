package net.nergi.solutions;

import java.util.ArrayList;
import java.util.List;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P5981 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "5981: Shapes";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    final ArrayList<Shape> shapes = new ArrayList<>(
        List.of(
            new Ellipse(3, 4),
            new Ellipse(4, 5),
            new Ellipse(6, 2),
            new Ellipse(2, 1),
            new Circle(10),
            new Circle(100),
            new Circle(1000),
            new Rectangle(3, 4),
            new Rectangle(12, 13),
            new Square(64)
        )
    );

    System.out.println("   Shapes: "
        + shapes.size());
    System.out.println(" Ellipses: "
        + shapes.stream().filter((s) -> s instanceof Ellipse).count());
    System.out.println("   Circle: "
        + shapes.stream().filter(Shape::isCircle).count());
    System.out.println("Rectangle: "
        + shapes.stream().filter((s) -> s instanceof Rectangle).count());
    System.out.println("   Square: "
        + shapes.stream().filter(Shape::isSquare).count());
  }

  public abstract static class Shape {

    protected Shape() {}

    public abstract void setSize(double... dims) throws IllegalArgumentException;

    public abstract double[] getSize();

    public boolean isSquare() {
      return this instanceof Square;
    }

    public boolean isCircle() {
      return this instanceof Circle;
    }

  }

  public static class Ellipse extends Shape {

    protected double semiMajorAxis;
    protected double semiMinorAxis;

    public Ellipse(double semiMajorAxis, double semiMinorAxis) {
      this.semiMajorAxis = semiMajorAxis;
      this.semiMinorAxis = semiMinorAxis;
    }

    @Override
    public void setSize(double... dims) throws IllegalArgumentException {
      if (dims.length != 2) {
        throw new IllegalArgumentException("Needs two arguments!");
      }

      this.semiMajorAxis = dims[0];
      this.semiMinorAxis = dims[1];
    }

    @Override
    public double[] getSize() {
      return new double[]{semiMajorAxis, semiMajorAxis};
    }

  }

  public static class Circle extends Ellipse {

    public Circle(double radius) {
      super(radius, radius);
    }

    @Override
    public void setSize(double... dims) throws IllegalArgumentException {
      if (dims.length != 1) {
        throw new IllegalArgumentException("Needs two arguments!");
      }

      this.semiMajorAxis = dims[0];
      this.semiMinorAxis = dims[0];
    }

    @Override
    public double[] getSize() {
      return new double[]{semiMajorAxis};
    }

  }

  public static class Rectangle extends Shape {

    protected double width;
    protected double height;

    public Rectangle(double width, double height) {
      this.width = width;
      this.height = height;
    }

    @Override
    public void setSize(double... dims) throws IllegalArgumentException {
      if (dims.length != 2) {
        throw new IllegalArgumentException("Needs two arguments!");
      }

      this.width = dims[0];
      this.height = dims[1];
    }

    @Override
    public double[] getSize() {
      return new double[]{width, height};
    }

  }

  public static class Square extends Rectangle {

    public Square(double sideLength) {
      super(sideLength, sideLength);
    }

    @Override
    public void setSize(double... dims) throws IllegalArgumentException {
      if (dims.length != 1) {
        throw new IllegalArgumentException("Needs two arguments!");
      }

      this.width = dims[0];
      this.height = dims[0];
    }

    @Override
    public double[] getSize() {
      return new double[]{width};
    }


  }

}
