package net.nergi.misc;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demo showcasing the usages of wait / notify for concurrent synchronisation. Uses a simple
 * producer / consumer model to aid the demonstration.
 *
 * <p>See the source code for more details.
 */
@SuppressWarnings("unused")
public class WaitNotifyDemo implements Runnable {

  private static final int PRODUCT_LIMIT = 200;

  private final Object sync = new Object();
  private final Deque<Ingredient> stash = new LinkedList<>();
  private final AtomicInteger products = new AtomicInteger(0);

  @Override
  public void run() {
    final Thread aProducer = new Thread(
        () -> {
          while (products.get() < PRODUCT_LIMIT) {
            obtainA();
          }
        },
        "A Producer");

    final Thread bProducer = new Thread(
        () -> {
          while (products.get() < PRODUCT_LIMIT) {
            obtainB();
          }
        },
        "B Producer");

    final Thread cProducer = new Thread(
        () -> {
          while (products.get() < PRODUCT_LIMIT) {
            obtainC();
          }
        },
        "C Producer");

    try {
      aProducer.start();
      bProducer.start();
      cProducer.start();

      aProducer.join();
      bProducer.join();
      cProducer.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      System.out.println("Made products: " + products + " / 200");
    }
  }

  private void obtainA() {
    synchronized (sync) {
      while (getACount() < 1L) {
        stash.add(new PartA());
      }

      sync.notifyAll();

      while (getBCount() < 2L && getCCount() < 4L) {
        try {
          if (products.get() >= PRODUCT_LIMIT) {
            return;
          }

          sync.wait();
        } catch (InterruptedException e) {
          throw new IllegalStateException(e);
        }
      }

      if (isReady()) {
        makeProduct();
      }
    }
  }

  private void obtainB() {
    synchronized (sync) {
      while (getBCount() < 2L) {
        stash.add(new PartB());
      }

      sync.notifyAll();

      while (getACount() < 1L && getCCount() < 4L) {
        try {
          if (products.get() >= PRODUCT_LIMIT) {
            return;
          }

          sync.wait();
        } catch (InterruptedException e) {
          throw new IllegalStateException(e);
        }
      }

      if (isReady()) {
        makeProduct();
      }
    }
  }

  private void obtainC() {
    synchronized (sync) {
      while (getCCount() < 4L) {
        stash.add(new PartC());
      }

      sync.notifyAll();

      while (getACount() < 1L && getBCount() < 2L) {
        try {
          if (products.get() >= PRODUCT_LIMIT) {
            return;
          }

          sync.wait();
        } catch (InterruptedException e) {
          throw new IllegalStateException(e);
        }
      }

      if (isReady()) {
        makeProduct();
      }
    }
  }

  private void makeProduct() {
    stash.remove(new PartA());
    stash.remove(new PartB());

    stash.remove(new PartB());
    stash.remove(new PartC());

    stash.remove(new PartC());
    stash.remove(new PartC());
    stash.remove(new PartC());

    products.incrementAndGet();

    synchronized (System.out) {
      if (products.get() > 0 && products.get() % 10 == 0) {
        System.out.println("10 products made!");
      }
    }
  }

  private long getACount() {
    return stash.stream().filter(i -> i.getClass() == PartA.class).count();
  }

  private long getBCount() {
    return stash.stream().filter(i -> i.getClass() == PartB.class).count();
  }

  private long getCCount() {
    return stash.stream().filter(i -> i.getClass() == PartC.class).count();
  }

  private boolean isReady() {
    return getACount() >= 1L && getBCount() >= 2L && getCCount() >= 4L;
  }

  // Base class that is used to form the products
  private interface Ingredient {}

  // We want a 1 : 2 : 4 ratio of A, B, C to produce a product.
  private static class PartA implements Ingredient {

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof PartA;
    }
  }

  private static class PartB implements Ingredient {

    @Override
    public int hashCode() {
      return 1;
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof PartB;
    }
  }

  private static class PartC implements Ingredient {

    @Override
    public int hashCode() {
      return 2;
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof PartC;
    }
  }
}
