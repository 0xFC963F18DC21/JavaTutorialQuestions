package net.nergi.solutions;

import java.io.IOException;
import net.nergi.Solution;
import net.nergi.Utils;

/** Solution for 17b1. */
@SuppressWarnings("unused")
public class P17b1 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "17b1: Default methods";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    System.out.println("There is no output expected for this. Please see the source code.");
  }

  public interface I {

    int foo();

    int foo(int x);

    int bar(int x);

    default int foobar() {
      return bar(foo());
    }

    default int foobar(int x) {
      return bar(foo(x));
    }
  }

  public interface J {

    int foo(int x);

    int bar(int x);

    default int foobar(int x) {
      return bar(foo(x));
    }
  }

  public interface K extends I, J {

    void baz();

    default int foobar(int x) {
      return I.super.foobar(x);
    }
  }

  public static class A implements I {

    @Override
    public int foo() {
      return 0;
    }

    @Override
    public int foo(int x) {
      return x;
    }

    @Override
    public int bar(int x) {
      return x * x;
    }

    public int foobar() {
      return 272387;
    }

    // Added original as this one does not return an int
    public void foobarOriginal(int x) {
      System.out.println(x * x * x);
    }
  }

  public static class B implements I, J {

    @Override
    public int foo() {
      return 1;
    }

    @Override
    public int foo(int x) {
      return x + 1;
    }

    @Override
    public int bar(int x) {
      return x * x + 1;
    }

    // Added methods to resolve conflicting default methods
    @Override
    public int foobar() {
      return 253846;
    }

    @Override
    public int foobar(int x) {
      return x * x * x + 1;
    }
  }

  public static class C implements K {

    @Override
    public int foo() {
      return 2;
    }

    @Override
    public int foo(int x) {
      return x + 2;
    }

    @Override
    public int bar(int x) {
      return x * x + 2;
    }

    @Override
    public void baz() {
      System.out.println("I am in C!");
    }

    // Added original as signature does not match
    public int foobarOriginal() throws IOException {
      try {
        return Integer.parseInt(Utils.getBr().readLine());
      } catch (NumberFormatException e) {
        return -1;
      }
    }

    // Added original as signature does not match
    protected int foobarOriginal(int x) {
      return x * x * x;
    }
  }
}
