package net.nergi.misc.objects.synchro.stack.lockfreestack;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import net.nergi.util.MethodNotImplementedException;
import net.nergi.misc.objects.synchro.stack.S0000Stack;

/**
 * Synchronised stack that uses atomic references to eliminate the need for locks.
 *
 * @param <E> Type of item in stack.
 */
public class LockfreeStack<E> implements S0000Stack<E> {

  private final MarkableNode<E> BOTTOM = new MarkableNode<>(null);

  private final AtomicInteger size = new AtomicInteger(0);
  private MarkableNode<E> top = BOTTOM;

  @Override
  public E peek() throws NoSuchElementException {
    do {
      if (top.getMark()) {
        if (top == BOTTOM) {
          throw new NoSuchElementException("Empty stack");
        } else {
          return top.getItem();
        }
      }
    } while (true);
  }

  @Override
  public void push(E item) {
    do {
      if (top.getMark()) {
        top = new MarkableNode<>(item, top);
        size.incrementAndGet();
        break;
      }
    } while (true);
  }

  @Override
  public E pop() throws NoSuchElementException {
    do {
      if (top.getMark()) {
        if (top == BOTTOM) {
          throw new NoSuchElementException("Empty stack");
        }

        final MarkableNode<E> oldTop = top;
        final E item = top.getItem();

        top = top.succ();
        oldTop.setFalse();

        size.decrementAndGet();

        return item;
      }
    } while (true);
  }

  @Override
  public boolean contains(E item) {
    // TODO: this
    throw new MethodNotImplementedException();
  }

  @Override
  public int size() {
    return size.get();
  }

  @Override
  public void clear() {
    top = BOTTOM;
    size.set(0);
  }
}
