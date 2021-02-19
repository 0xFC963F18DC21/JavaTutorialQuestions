package net.nergi.misc;

import net.nergi.misc.objects.synchro.StackPerformanceTester;
import net.nergi.misc.objects.synchro.coarselinkedstack.CoarseLinkedStack;
import net.nergi.misc.objects.synchro.linkedstack.LinkedStack;

/**
 * Compares the performance of different locking mechanisms when using stacks in a concurrent
 * execution environment.
 */
public class StackPerformanceComparison implements Runnable {

  @Override
  @SuppressWarnings("unchecked")
  public void run() {
    final var unsyncTester =
        StackPerformanceTester.generateTesterUnit(LinkedStack.class, Object::new);

    final var coarseTester =
        StackPerformanceTester.generateTesterUnit(CoarseLinkedStack.class, Object::new);

    System.out.println("Non-synchronised stack");

    unsyncTester.test6400Pops();
    unsyncTester.test6400Pushes();
    unsyncTester.test3200Each();
    unsyncTester.test6000Pops400Pushes();
    unsyncTester.test400Pops6000Pushes();

    System.out.println("\nCoarsely-synchronised stack");

    coarseTester.test6400Pops();
    coarseTester.test6400Pushes();
    coarseTester.test3200Each();
    coarseTester.test6000Pops400Pushes();
    coarseTester.test400Pops6000Pushes();
  }
}
