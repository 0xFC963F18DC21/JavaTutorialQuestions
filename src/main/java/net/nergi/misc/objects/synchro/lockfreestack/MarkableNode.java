package net.nergi.misc.objects.synchro.lockfreestack;

import java.util.concurrent.atomic.AtomicMarkableReference;
import net.nergi.misc.objects.synchro.S0000StackNode;

/**
 * A markable node that supports atomic operations.
 *
 * @param <E> Type of item in node
 */
public class MarkableNode<E> extends S0000StackNode<E, MarkableNode<E>> {

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
    this.successor.compareAndSet(
        this.successor.getReference(), successor, true, true);
  }

  @Override
  public MarkableNode<E> succ() {
    if (successor.isMarked()) {
      return successor.getReference();
    } else {
      return null;
    }
  }

  public void setTrue() {
    this.successor.compareAndSet(
        this.successor.getReference(), this.successor.getReference(), false, true);
  }

  public void setFalse() {
    this.successor.compareAndSet(
        this.successor.getReference(), this.successor.getReference(), true, false);
  }
}
