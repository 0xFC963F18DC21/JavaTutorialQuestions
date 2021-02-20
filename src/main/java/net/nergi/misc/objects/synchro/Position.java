package net.nergi.misc.objects.synchro;

/**
 * Holds two stack nodes; a node and its predecessor.
 *
 * @param <E> Type of item held in nodes.
 */
public class Position<E, T extends S0000StackNode<E, T>> {

  public final T predecessor;
  public final T node;

  public Position(T predecessor, T node) {
    this.predecessor = predecessor;
    this.node = node;
  }
}
