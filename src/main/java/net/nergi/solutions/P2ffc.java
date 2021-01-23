package net.nergi.solutions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.nergi.Solution;
import net.nergi.solutions.P735a.GenericCollection;
import net.nergi.solutions.P735a.GenericIterator;

@SuppressWarnings("unused")
public class P2ffc implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "2ffc: Generic stacks";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    // Test new generic stacks
    final GenericStack<Integer> intStk = new GenericStackList<>();

    intStk.push(5);
    intStk.push(3);
    intStk.push(2);
    intStk.push(1);
    intStk.push(1);

    final GenericStack<String> strStk = new GenericStackArray<>();

    strStk.push("dead");
    strStk.push("already");
    strStk.push("are");
    strStk.push("you");

    System.out.println(intStk);
    System.out.println(strStk);
  }

  /**
   * Generic stack interface for holding any reference type.
   * @param <E> Type to store
   */
  // Modified for 735a
  public interface GenericStack<E> extends GenericCollection<E> {

    void push(E item);

    E pop();

    boolean isEmpty();

  }

  /**
   * Abstract class which all generic stacks inherit from.
   * @param <E> Type to store
   */
  public abstract static class AbstractGenericStack<E> implements GenericStack<E> {

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("[");

      for (E e : this) {
        if (sb.length() > 1) {
          sb.append(", ");
        }

        sb.append(e);
      }

      sb.append("]");

      return sb.toString();
    }

  }

  @SuppressWarnings("unchecked")
  public static class GenericStackArray<E> extends AbstractGenericStack<E> {

    private int currentPointer = 0;
    private final Object[] backingArray;

    // Creates an empty stack of E
    public GenericStackArray() {
      this(100);
    }

    // Creates an empty stack of E
    public GenericStackArray(int capacity) {
      this.backingArray = new Object[capacity];
    }

    // If the stack is full, does nothing.
    // Otherwise, pushes the given E on to the top of the stack
    @Override
    public void push(E item) {
      if (currentPointer >= backingArray.length) {
        return;
      }

      backingArray[currentPointer++] = item;
    }

    // If the stack is empty, leaves the stack unchanged and returns
    // null. Otherwise, removes the item that is on the top of
    // the stack and returns it
    @Override
    public E pop() {
      if (isEmpty()) {
        return null;
      }

      // Strings do NOT get cleared from the array
      return (E) backingArray[--currentPointer];
    }

    // Returns true iff the stack is empty
    @Override
    public boolean isEmpty() {
      return currentPointer == 0;
    }

    // Extension for 85bb
    // NOT THREAD SAFE
    @Override
    public GenericIterator<E> iterator() {
      return new GenericIterator<>() {

        private int counter = currentPointer;

        @Override
        public boolean hasNext() {
          return counter != 0;
        }

        @Override
        public E next() {
          return (E) backingArray[--counter];
        }

      };
    }

  }

  public static class GenericStackList<E> extends AbstractGenericStack<E> {

    private final List<E> backingList;

    // Creates an empty stack of E
    public GenericStackList() {
      this.backingList = new LinkedList<>();
    }

    // If the stack is full, does nothing.
    // Otherwise, pushes the given item on to the top of the stack
    @Override
    public void push(E item) {
      backingList.add(item);
    }

    // If the stack is empty, leaves the stack unchanged and returns
    // null. Otherwise, removes the string that is on the top of
    // the stack and returns it
    @Override
    public E pop() {
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

    // NOT THREAD SAFE
    @Override
    public GenericIterator<E> iterator() {
      return new GenericIterator<>() {

        private int counter = backingList.size();

        @Override
        public boolean hasNext() {
          return counter != 0;
        }

        @Override
        public E next() {
          return backingList.get(--counter);
        }

      };
    }

  }

}
