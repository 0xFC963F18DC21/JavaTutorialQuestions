package net.nergi.solutions;

import net.nergi.Solution;

@SuppressWarnings("unused")
public class P6346 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "6346: Depth of arithmetic expressions";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    Expr testExpr = new FactExpr(
        new AddExpr(
            new LiteralExpr("3"),
            new MulExpr(
                new LiteralExpr("4"),
                new LiteralExpr("5")
            )
        )
    );

    System.out.println(testExpr + "\n" + testExpr.depth());
  }

  private interface Expr {
    int depth();
  }

  private static class LiteralExpr implements Expr {

    private final String literal;

    public LiteralExpr(String literal) {
      this.literal = literal;
    }

    @Override
    public int depth() {
      return 0;
    }

    @Override
    public String toString() {
      return literal;
    }
  }

  private static class AddExpr implements Expr {

    private final Expr left;
    private final Expr right;

    public AddExpr(Expr left, Expr right) {
      this.left = left;
      this.right = right;
    }

    @Override
    public int depth() {
      return 1 + Math.max(left.depth(), right.depth());
    }

    @Override
    public String toString() {
      return String.format("(%s + %s)", left, right);
    }
  }

  private static class MulExpr implements Expr {

    private final Expr left;
    private final Expr right;

    public MulExpr(Expr left, Expr right) {
      this.left = left;
      this.right = right;
    }

    @Override
    public int depth() {
      return 1 + Math.max(left.depth(), right.depth());
    }

    @Override
    public String toString() {
      return String.format("(%s * %s)", left, right);
    }
  }

  private static class FactExpr implements Expr {

    private final Expr operand;

    public FactExpr(Expr operand) {
      this.operand = operand;
    }

    @Override
    public int depth() {
      return 1 + operand.depth();
    }

    @Override
    public String toString() {
      return String.format("(%s)!", operand);
    }
  }
}
