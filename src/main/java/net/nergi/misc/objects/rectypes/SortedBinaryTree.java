package net.nergi.misc.objects.rectypes;

/**
 * An automatically-sorted binary tree.
 *
 * @param <T> Type held in the tree. Must implement {@link Comparable}.
 */
public class SortedBinaryTree<T extends Comparable<T>> extends BinaryTree<T> {

  private static final String MESSAGE = "You cannot assign children manually to this tree.";

  /**
   * Make a new sorted binary tree with the given item.
   *
   * @param item Item to be held in the tree
   */
  public SortedBinaryTree(T item) {
    super(item);
  }

  @Override
  public void add(T item) {
    if (item.compareTo(getItem()) <= 0) {
      if (getLeft() == null) {
        super.setLeft(new SortedBinaryTree<>(item));
      } else {
        getLeft().add(item);
      }
    } else {
      if (getRight() == null) {
        super.setRight(new SortedBinaryTree<>(item));
      } else {
        getRight().add(item);
      }
    }
  }

  // TODO: override contains for this type

  /** DO NOT USE. THIS CONSTRUCTOR IS INVALID. */
  public SortedBinaryTree(T item, BinaryTree<T> left, BinaryTree<T> right) {
    super(item, left, right);
    throw new UnsupportedOperationException(MESSAGE);
  }

  /** DO NOT USE. THIS METHOD IS INVALID. */
  @Override
  public void setLeft(BinaryTree<T> left) {
    throw new UnsupportedOperationException(MESSAGE);
  }

  /** DO NOT USE. THIS METHOD IS INVALID. */
  @Override
  public void setRight(BinaryTree<T> right) {
    throw new UnsupportedOperationException(MESSAGE);
  }

}
