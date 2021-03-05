package net.nergi.misc.objects.synchro.stack;

import java.util.NoSuchElementException;

/**
 * A (possibly) synchronised stack interface.
 *
 * @param <E> Type held inside stack
 */
public interface S0000Stack<E> {

  /**
   * Peek at the topmost element without popping it.
   *
   * @return Topmost element of the stack.
   * @throws NoSuchElementException If there are no elements in the stack.
   */
  E peek() throws NoSuchElementException;

  /**
   * Push an item onto the stack.
   *
   * @param item Item to push
   */
  void push(E item);

  /**
   * Pop an item from the stack.
   *
   * @return Topmost item from the stack.
   * @throws NoSuchElementException If no elements are in the stack.
   */
  E pop() throws NoSuchElementException;

  /**
   * Check if an item exists in the stack.
   *
   * @param item Item to check its existence of.
   * @return <code>true</code> if an item exists in the stack, <code>false</code> otherwise.
   */
  boolean contains(E item);

  /**
   * Get the amount of items stored in the stack.
   *
   * @return Amount of items in the stack.
   */
  int size();

  /** Clear the current stack. */
  void clear();
}
