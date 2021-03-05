package net.nergi.misc.objects.synchro.stack.linkedstack;

import java.util.NoSuchElementException;
import net.nergi.misc.objects.synchro.stack.S0000Stack;

/**
 * A simple unsynchronised linked stack.
 *
 * @param <E> Type of item in stack.
 */
public class LinkedStack<E> implements S0000Stack<E> {

  private LinkedStackNode<E> top;
  private int size = 0;

  @Override
  public E peek() throws NoSuchElementException {
    return top.getItem();
  }

  @Override
  public void push(E item) {
    ++size;
    top = new LinkedStackNode<>(item, top);
  }

  @Override
  public E pop() throws NoSuchElementException {
    if (top == null) {
      throw new NoSuchElementException("Empty stack");
    } else {
      final var old = top;
      top = top.succ();

      --size;
      return old.getItem();
    }
  }

  @Override
  public boolean contains(E item) {
    LinkedStackNode<E> currentTop = top;
    while (currentTop != null) {
      if (currentTop.getItem().equals(item)) {
        return true;
      }

      currentTop = currentTop.succ();
    }

    return false;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void clear() {
    top = null;
  }
}
