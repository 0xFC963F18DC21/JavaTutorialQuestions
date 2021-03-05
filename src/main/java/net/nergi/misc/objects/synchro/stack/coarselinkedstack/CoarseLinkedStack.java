package net.nergi.misc.objects.synchro.stack.coarselinkedstack;

import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import net.nergi.misc.objects.synchro.stack.S0000Stack;
import net.nergi.misc.objects.synchro.stack.linkedstack.LinkedStackNode;

/**
 * A simple coarsely synchronized linked stack.
 *
 * @param <E> Type of item in stack.
 */
public class CoarseLinkedStack<E> implements S0000Stack<E> {

  private final Lock stkLock = new ReentrantLock(true);

  private LinkedStackNode<E> top;
  private int size = 0;

  @Override
  public E peek() throws NoSuchElementException {
    try {
      stkLock.lock();
      return top.getItem();
    } finally {
      stkLock.unlock();
    }
  }

  @Override
  public void push(E item) {
    try {
      stkLock.lock();

      ++size;
      top = new LinkedStackNode<>(item, top);
    } finally {
      stkLock.unlock();
    }
  }

  @Override
  public E pop() throws NoSuchElementException {
    try {
      stkLock.lock();

      if (top == null) {
        throw new NoSuchElementException("Empty stack");
      } else {
        final var old = top;
        top = top.succ();

        --size;
        return old.getItem();
      }
    } finally {
      stkLock.unlock();
    }
  }

  @Override
  public boolean contains(E item) {
    try {
      stkLock.lock();
      LinkedStackNode<E> currentTop = top;
      while (currentTop != null) {
        if (currentTop.getItem().equals(item)) {
          return true;
        }

        currentTop = currentTop.succ();
      }

      return false;
    } finally {
      stkLock.unlock();
    }
  }

  @Override
  public int size() {
    try {
      stkLock.lock();
      return size;
    } finally {
      stkLock.unlock();
    }
  }

  @Override
  public void clear() {
    try {
      stkLock.lock();
      top = null;
    } finally {
      stkLock.unlock();
    }
  }
}
