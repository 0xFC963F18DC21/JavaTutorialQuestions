package net.nergi.misc.objects.synchro.stack;

/**
 * Representation of a linked node in a stack.
 *
 * @param <E> Type of item held in node.
 * @param <T> Extending subclass.
 */
public abstract class S0000StackNode<E, T extends S0000StackNode<E, T>> {

  private final E item;
  protected T successor;

  protected S0000StackNode(E item) {
    this(item, null);
  }

  protected S0000StackNode(E item, T successor) {
    this.item = item;
    this.successor = successor;
  }

  /**
   * Get this node's item.
   *
   * @return Node item.
   */
  public E getItem() {
    return item;
  }

  /**
   * Set a new successor node.
   *
   * @param successor New successor node
   */
  public abstract void putSucc(T successor);

  /**
   * Get the node's successor node.
   *
   * @return Successor node.
   */
  public abstract T succ();
}
