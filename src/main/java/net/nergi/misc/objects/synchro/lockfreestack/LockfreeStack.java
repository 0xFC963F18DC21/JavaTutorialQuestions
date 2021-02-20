package net.nergi.misc.objects.synchro.lockfreestack;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicMarkableReference;
import net.nergi.misc.objects.synchro.S0000Stack;

/**
 * Synchronised stack that uses atomic references to eliminate the need for locks.
 *
 * @param <E> Type of item in stack.
 */
public class LockfreeStack<E> implements S0000Stack<E> {

  private final AtomicMarkableReference<MarkableNode<E>> BOTTOM =
      new AtomicMarkableReference<>(new MarkableNode<>(null), true);

  private AtomicMarkableReference<MarkableNode<E>> top = BOTTOM;

  @Override
  public E peek() throws NoSuchElementException {
    do {
      if (top.isMarked()) {
        if (top == BOTTOM) {
          throw new NoSuchElementException("Empty stack");
        } else {
          return top.getReference().getItem();
        }
      }
    } while (true);
  }

  @Override
  public void push(E item) {
    do {
      if (top.isMarked()) {
        top = new AtomicMarkableReference<>(new MarkableNode<>(item), true);
        break;
      }
    } while (true);
  }

  @Override
  public E pop() throws NoSuchElementException {
    do {
      if (top.isMarked()) {
        if (top == BOTTOM) {
          throw new NoSuchElementException("Empty stack");
        } else {
          final var oldTop = top;

          oldTop.set(oldTop.getReference(), false);
          oldTop.getReference().setFalse();

          top = new AtomicMarkableReference<>(oldTop.getReference(), true);
          return oldTop.getReference().getItem();
        }
      }
    } while (true);
  }

  @Override
  public boolean contains(E item) {
    return false;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public void clear() {

  }
}
