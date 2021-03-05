package net.nergi.misc;

import net.nergi.misc.objects.synchro.stack.StackPerformanceTester;
import net.nergi.misc.objects.synchro.stack.coarselinkedstack.CoarseLinkedStack;
import net.nergi.misc.objects.synchro.stack.linkedstack.LinkedStack;
import net.nergi.misc.objects.synchro.stack.lockfreestack.LockfreeStack;

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

    final var lockfreeTester =
        StackPerformanceTester.generateTesterUnit(LockfreeStack.class, Object::new);

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

    System.out.println("\nLock-free stack");

    lockfreeTester.test6400Pops();
    lockfreeTester.test6400Pushes();
    lockfreeTester.test3200Each();
    lockfreeTester.test6000Pops400Pushes();
    lockfreeTester.test400Pops6000Pushes();
  }
}
