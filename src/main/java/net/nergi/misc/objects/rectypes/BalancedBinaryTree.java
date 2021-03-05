package net.nergi.misc.objects.rectypes;

/**
 * A forcefully-balanced binary tree. One cannot manually assign children to this tree.
 *
 * @param <T> Type held inside tree.
 */
public class BalancedBinaryTree<T> extends BinaryTree<T> {

  private static final String MESSAGE = "You cannot assign children manually to this tree.";

  /**
   * Make a new balanced binary tree.
   *
   * @param item Item to hold in tree
   */
  public BalancedBinaryTree(T item) {
    super(item);
  }

  @Override
  public void add(T item) {
    if (getLeft() == null) {
      super.setLeft(new BalancedBinaryTree<>(item));
    } else if (getRight() == null) {
      super.setRight(new BalancedBinaryTree<>(item));
    }

    final int lSize = getLeft().size();
    final int rSize = getRight().size();

    if (rSize < lSize) {
      getRight().add(item);
    } else {
      getLeft().add(item);
    }
  }

  /** DO NOT USE. THIS CONSTRUCTOR IS INVALID. */
  public BalancedBinaryTree(T item, BinaryTree<T> left, BinaryTree<T> right) {
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
