package net.nergi.misc.objects.synchro.queue.coarselinkedqueue;

/**
 * A very basic queue node. Offers no synchronisation* on its own.
 *
 * <p>* an exception being with wait / notify methods.
 *
 * @param <T> Type held inside the node.
 */
public class BasicNode<T> {

  private final T item;
  private BasicNode<T> predecessor;
  private BasicNode<T> successor;

  public BasicNode(T item) {
    this.item = item;
  }

  public T getItem() {
    return item;
  }

  public BasicNode<T> getPredecessor() {
    return predecessor;
  }

  public void setPredecessor(
      BasicNode<T> predecessor) {
    this.predecessor = predecessor;
  }

  public BasicNode<T> getSuccessor() {
    return successor;
  }

  public void setSuccessor(BasicNode<T> successor) {
    this.successor = successor;
  }
}
