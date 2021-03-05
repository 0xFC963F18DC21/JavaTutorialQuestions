package net.nergi.misc.objects.synchro.stack.linkedstack;

import net.nergi.misc.objects.synchro.stack.S0000StackNode;

/**
 * Simple linked stack node. Unsynchronised.
 *
 * @param <E> Type of item in stack.
 */
public class LinkedStackNode<E> extends S0000StackNode<E, LinkedStackNode<E>> {

  /**
   * Create a new node with the given item.
   *
   * @param item Item to put in node
   */
  public LinkedStackNode(E item) {
    super(item);
  }

  /**
   * Create a new node with the given item and successor.
   *
   * @param item      Item to put in node
   * @param successor Node's successor
   */
  public LinkedStackNode(E item, LinkedStackNode<E> successor) {
    super(item, successor);
  }

  @Override
  public void putSucc(LinkedStackNode<E> successor) {
    this.successor = successor;
  }

  @Override
  public LinkedStackNode<E> succ() {
    return successor;
  }
}
