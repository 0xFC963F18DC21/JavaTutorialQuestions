package net.nergi.misc.objects.synchro.queue.coarselinkedqueue;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * An example queue implementation that uses a coarse lock. The whole structure is locked whenever
 * an operation is performed.
 *
 * @param <T> Type held inside queue.
 */
public class CoarseLinkedQueue<T> implements Queue<T> {

  private final ReentrantLock lock = new ReentrantLock();
  private final AtomicInteger size = new AtomicInteger(0);
  private final AtomicInteger iteratorViews = new AtomicInteger(0);

  private BasicNode<T> head = null;
  private BasicNode<T> tail = null;

  @Override
  public int size() {
    try {
      lock.lock();
      return size.get();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean isEmpty() {
    return size() <= 0;
  }

  @Override
  public boolean contains(Object o) {
    try {
      lock.lock();

      for (BasicNode<T> curr = head; curr != null; curr = curr.getSuccessor()) {
        if (curr.getItem().equals(o)) {
          return true;
        }
      }

      return false;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public Iterator<T> iterator() {
    try {
      lock.lock();
      return new Iterator<>() {
        BasicNode<T> currentHead = head;

        {
          iteratorViews.incrementAndGet();
        }

        @Override
        public boolean hasNext() {
          return head != null;
        }

        @Override
        public T next() {
          final T item = currentHead.getItem();
          currentHead = currentHead.getSuccessor();

          if (!hasNext()) {
            iteratorViews.decrementAndGet();
          }

          return item;
        }
      };
    } finally {
      lock.unlock();
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public T[] toArray() {
    try {
      lock.lock();

      final T[] result = (T[]) new Object[size.get()];
      int i = 0;

      for (BasicNode<T> curr = head; curr != null; curr = curr.getSuccessor()) {
        result[i++] = curr.getItem();
      }

      return result;
    } finally {
      lock.unlock();
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T1> T1[] toArray(T1[] a) {
    try {
      lock.lock();

      int i = 0;
      for (BasicNode<T> curr = head; curr != null; curr = curr.getSuccessor()) {
        a[i++] = (T1) curr.getItem();
      }

      return a;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean add(T t) {
    if (iteratorViews.get() > 0) {
      throw new ConcurrentModificationException("Modification attempted while iterating!");
    }

    try {
      lock.lock();

      if (head == null) {
        head = new BasicNode<>(t);
        tail = head;
      } else {
        final BasicNode<T> newNode = new BasicNode<>(t);
        tail.setSuccessor(newNode);
        newNode.setPredecessor(tail);
      }

      size.incrementAndGet();
      return true;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean remove(Object o) {
    if (iteratorViews.get() > 0) {
      throw new ConcurrentModificationException("Modification attempted while iterating!");
    }

    return false;
  }

  @Override
  public T remove() {
    if (iteratorViews.get() > 0) {
      throw new ConcurrentModificationException("Modification attempted while iterating!");
    }

    try {
      lock.lock();

      if (head == null) {
        throw new NoSuchElementException("Empty queue.");
      } else {
        final T item = head.getItem();
        head = head.getSuccessor();

        if (head == null) {
          tail = null;
        } else {
          head.setPredecessor(null);
        }

        return item;
      }
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return withLock(() -> {
      for (Object item : c) {
        if (!contains(item)) {
          return false;
        }
      }

      return true;
    }, false);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    return withLock(() -> {
      for (T item : c) {
        add(item);
      }

      return true;
    }, false);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return withLock(() -> {
      boolean flag = false;
      for (Object item : c) {
        if (remove(item)) {
          flag = true;
        }
      }

      return flag;
    }, false);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    try {
      lock.lock();

      boolean flag = false;

      for (BasicNode<T> curr = head; curr != null; curr = curr.getSuccessor()) {
        if (!c.contains(curr.getItem())) {
          final BasicNode<T> pred = curr.getPredecessor();
          final BasicNode<T> succ = curr.getSuccessor();

          pred.setSuccessor(succ);
          succ.setPredecessor(pred);
        }
      }

      return flag;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void clear() {
    if (iteratorViews.get() > 0) {
      throw new ConcurrentModificationException("Modification attempted while iterating!");
    }

    withLock(() -> {
      head = null;
      tail = null;
      size.set(0);
    });
  }

  @Override
  public boolean offer(T t) {
    return add(t);
  }

  @Override
  public T poll() {
    if (iteratorViews.get() > 0) {
      throw new ConcurrentModificationException("Modification attempted while iterating!");
    }

    try {
      lock.lock();

      if (head == null) {
        return null;
      } else {
        final T item = head.getItem();
        head = head.getSuccessor();

        if (head == null) {
          tail = null;
        } else {
          head.setPredecessor(null);
        }

        return item;
      }
    } finally {
      lock.unlock();
    }
  }

  @Override
  public T element() {
    try {
      lock.lock();

      if (head == null) {
        throw new NoSuchElementException("Empty queue.");
      } else {
        return head.getItem();
      }
    } finally {
      lock.unlock();
    }
  }

  @Override
  public T peek() {
    try {
      lock.lock();

      return head == null ? null : head.getItem();
    } finally {
      lock.unlock();
    }
  }

  private <U> U withLock(Callable<U> c, U defaultValue) {
    try {
      lock.lock();
      return c.call();
    } catch (Exception e) {
      e.printStackTrace();
      return defaultValue;
    } finally {
      lock.unlock();
    }
  }

  private void withLock(Runnable r) {
    try {
      lock.lock();
      r.run();
    } finally {
      lock.unlock();
    }
  }
}
