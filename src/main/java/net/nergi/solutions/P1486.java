package net.nergi.solutions;

import java.util.LinkedList;
import java.util.List;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P1486 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "1486: String stack";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    // Stacks
    final StringStack sstka = new StringStackArray();
    final StringStack sstkl = new StringStackList();

    sstka.push("this");
    sstka.push("is");
    sstka.push("my");
    sstka.push("message");
    sstka.push("to");
    sstka.push("my");
    sstka.push("master");

    sstkl.push("win");
    sstkl.push("cannot");
    sstkl.push("you");
    sstkl.push("fight");
    sstkl.push("a");
    sstkl.push("is");
    sstkl.push("this");

    // Transfer the array stack to the list stack
    transferStacks(sstkl, sstka);

    // Assertion
    System.out.printf("Is sstka empty? %s\n", sstka.isEmpty());

    // Print all in second stack
    while (!sstkl.isEmpty()) {
      System.out.printf("%s ", sstkl.pop());
    }

    System.out.print('\n');
  }

  public interface StringStack {

    void push(String s);

    String pop();

    boolean isEmpty();

  }

  public static void transferStacks(StringStack dst, StringStack src) {
    while (!src.isEmpty()) {
      dst.push(src.pop());
    }
  }

  public static class StringStackArray implements StringStack {

    private int currentPointer = 0;
    private final String[] backingArray;

    // Creates an empty string stack
    public StringStackArray() {
      this(100);
    }

    // Creates an empty string stack
    public StringStackArray(int capacity) {
      this.backingArray = new String[capacity];
    }

    // If the stack is full, does nothing.
    // Otherwise, pushes the given String on to the top of the stack
    @Override
    public void push(String s) {
      if (currentPointer >= backingArray.length) {
        return;
      }

      backingArray[currentPointer++] = s;
    }

    // If the stack is empty, leaves the stack unchanged and returns
    // null.  Otherwise, removes the string that is on the top of
    // the stack and returns it
    @Override
    public String pop() {
      if (isEmpty()) {
        return null;
      }

      // Strings do NOT get cleared from the array
      return backingArray[--currentPointer];
    }

    // Returns true iff the stack is empty
    @Override
    public boolean isEmpty() {
      return currentPointer == 0;
    }

  }

  public static class StringStackList implements StringStack {

    private final List<String> backingList;

    // Creates an empty string stack
    public StringStackList() {
      this.backingList = new LinkedList<>();
    }

    // If the stack is full, does nothing.
    // Otherwise, pushes the given String on to the top of the stack
    @Override
    public void push(String s) {
      backingList.add(s);
    }

    // If the stack is empty, leaves the stack unchanged and returns
    // null.  Otherwise, removes the string that is on the top of
    // the stack and returns it
    @Override
    public String pop() {
      if (isEmpty()) {
        return null;
      }

      return backingList.remove(backingList.size() - 1);
    }

    // Returns true iff the stack is empty
    @Override
    public boolean isEmpty() {
      return backingList.isEmpty();
    }

  }

}
