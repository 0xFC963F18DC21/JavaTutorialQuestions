package net.nergi.misc.objects.synchro.lockfreestack;

import java.util.concurrent.atomic.AtomicMarkableReference;
import net.nergi.misc.objects.synchro.S0000StackNode;

/**
 * A markable node that supports atomic operations.
 *
 * @param <E> Type of item in node
 */
public class MarkableNode<E> extends S0000StackNode<E, MarkableNode<E>> {

  private boolean valid = true;
  private AtomicMarkableReference<MarkableNode<E>> successor;

  /**
   * Create a new node with the given item.
   *
   * @param item Item to put in node
   */
  public MarkableNode(E item) {
    super(item);
  }

  /**
   * Create a new node with the given item and successor.
   *
   * @param item      Item to put in node
   * @param successor Node's successor
   */
  public MarkableNode(E item, MarkableNode<E> successor) {
    super(item, successor);
  }

  @Override
  public void putSucc(MarkableNode<E> successor) {
    throw new UnsupportedOperationException("Use putSuccIfValid instead!");
  }

  public boolean putSuccIfValid(MarkableNode<E> expected, MarkableNode<E> newSuccessor) {
    return successor.compareAndSet(expected, newSuccessor, true, true);
  }

  @Override
  public MarkableNode<E> succ() {
    if (successor == null) {
      return null;
    }

    return successor.getReference();
  }

  /**
   * Gets the validity mark of this node.
   *
   * @return Validity mark
   */
  public boolean getMark() {
    if (successor == null) {
      return true;
    }

    return successor.isMarked();
  }

  /** Set the mark to true. */
  public boolean setTrue() {
    if (successor == null) {
      return false;
    }

    return successor.attemptMark(succ(), true);
  }

  /** Set the mark to false. */
  public boolean setFalse() {
    if (successor == null) {
      return false;
    }

    final MarkableNode<E> scc = succ();
    return successor.compareAndSet(scc, scc, true, false);
  }
}
