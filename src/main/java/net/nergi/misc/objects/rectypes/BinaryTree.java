package net.nergi.misc.objects.rectypes;

import java.util.ArrayList;
import java.util.List;

/**
 * A rudimentary binary tree type. Does not have to be balanced.
 *
 * @param <T> Type of item held in tree.
 */
public class BinaryTree<T> {

  private final T item;
  private BinaryTree<T> left = null;
  private BinaryTree<T> right = null;

  /**
   * Make a new binary tree that holds the given item. Its children are set to null.
   *
   * @param item Item to hold in tree
   */
  public BinaryTree(T item) {
    this.item = item;
  }

  /**
   * Make a new binary tree that holds the given item and child trees.
   *
   * @param item  Item to hold in tree
   * @param left  Left child of tree
   * @param right Right child of tree
   */
  public BinaryTree(T item, BinaryTree<T> left, BinaryTree<T> right) {
    this.item = item;
    this.left = left;
    this.right = right;
  }

  /**
   * Add a new item to the tree. Is only implemented for balanced or sorted trees.
   *
   * @param item Item to add to tree.
   */
  public void add(T item) {
    throw new UnsupportedOperationException("This tree does not know what to do.");
  }

  public T getItem() {
    return item;
  }

  public BinaryTree<T> getLeft() {
    return left;
  }

  public void setLeft(BinaryTree<T> left) {
    this.left = left;
  }

  public BinaryTree<T> getRight() {
    return right;
  }

  public void setRight(BinaryTree<T> right) {
    this.right = right;
  }

  /**
   * Traverse the tree, returning its items by the given ordering.
   *
   * @param order Order of traversal
   * @return Flattened tree with the given order.
   */
  public List<T> traverse(TraversalOrder order) {
    // Return singleton list if the current branch is a leaf.
    if (isLeaf()) {
      return List.of(item);
    }

    // Collect lists from L/R traversals.
    final List<T> leftList = left == null ? List.of() : left.traverse(order);
    final List<T> rightList = right == null ? List.of() : right.traverse(order);

    final List<T> resultList = new ArrayList<>();
    switch (order) {
      case PRE -> {
        resultList.add(item);
        resultList.addAll(leftList);
        resultList.addAll(rightList);
      }
      case IN_ORDER -> {
        resultList.addAll(leftList);
        resultList.add(item);
        resultList.addAll(rightList);
      }
      case POST -> {
        resultList.addAll(leftList);
        resultList.addAll(rightList);
        resultList.add(item);
      }
      default -> throw new IllegalStateException("Should be unreachable.");
    }

    return resultList;
  }

  /**
   * Checks if an item exists in the tree.
   *
   * @param item Item to check for
   * @return <code>true</code> if item exists in the tree, <code>false</code> otherwise.
   */
  public boolean contains(T item) {
    if (this.item.equals(item)) {
      return true;
    } else if (isLeaf()) {
      return false;
    } else {
      return (left != null && left.contains(item))
          || (right != null && right.contains(item));
    }
  }

  /**
   * Calculates the number of items stored in this tree.
   *
   * @return Number of items stored in this tree.
   */
  public int size() {
    final int lSize = left == null ? 0 : left.size();
    final int rSize = right == null ? 0 : right.size();
    return 1 + lSize + rSize;
  }

  /**
   * Checks whether the current node is a branch node, and not a leaf node.
   *
   * @return <code>true</code> if the current node is a branch node, <code>false</code> otherwise.
   */
  public boolean isBranch() {
    return !isLeaf();
  }

  /**
   * Checks whether the current node is a leaf node, and not a branch node.
   *
   * @return <code>true</code> if the current node is a leaf node, <code>false</code> otherwise.
   */
  public boolean isLeaf() {
    return left == null && right == null;
  }

  /** Encodes a traversal order when navigating a tree. */
  public enum TraversalOrder {
    PRE, IN_ORDER, POST
  }
}
