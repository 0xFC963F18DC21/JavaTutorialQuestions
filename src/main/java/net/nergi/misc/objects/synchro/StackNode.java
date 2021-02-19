package net.nergi.misc.objects.synchro;

/**
 * Representation of a linked node in a stack.
 *
 * @param <E> Type of item held in node.
 * @param <T> Extending subclass.
 */
public abstract class StackNode<E, T extends StackNode<E, T>> {

  private final E item;
  protected T successor;

  protected StackNode(E item) {
    this(item, null);
  }

  protected StackNode(E item, T successor) {
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
