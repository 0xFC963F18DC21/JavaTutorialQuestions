package net.nergi.solutions;

import net.nergi.Solution;
import net.nergi.Utils;

/** Solution for 96df. */
@SuppressWarnings("unused")
public class P96df implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "96df: Tree nodes";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    // Make tree
    final Tree<Integer> intTree =
        new TreeNode<>(
            0,
            new BinaryNode<>(
                1,
                new LeafNode<>(2),
                new LeafNode<>(3)
            ),
            new LeafNode<>(4),
            new TreeNode<>(
                5,
                new LeafNode<>(6),
                new LeafNode<>(7),
                new LeafNode<>(8),
                new LeafNode<>(9)
            )
        );

    System.out.println(intTree);
  }

  public abstract static class Tree<E> {

    private E key;

    // Hide constructor.
    private Tree() {}

    public abstract int getNumberOfChildren();

    public abstract Tree<E> getChild(int childIndex);

    public abstract void setChild(int childIndex, Tree<E> child);

    public E getKey() {
      return key;
    }

    public void setKey(E key) {
      this.key = key;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder(key.toString());
      if (getNumberOfChildren() <= 0) {
        return sb.toString();
      }

      sb.append(" (");

      for (int i = 0; i < getNumberOfChildren(); ++i) {
        sb.append(getChild(i).toString())
            .append(", ");
      }

      sb.setLength(sb.length() - 2); // Remove trailing ", "
      sb.append(")");

      return sb.toString();
    }

  }

  // Template code from problem text, modified to fit my new structure.
  public static class TreeNode<E> extends Tree<E> {

    private final Tree<E>[] children;

    @SuppressWarnings("unchecked")
    public TreeNode(int numberOfChildren) {
      children = (Tree<E>[]) new Tree[numberOfChildren];
    }

    @SuppressWarnings("unchecked")
    public TreeNode(int numberOfChildren, E key) {
      children = (Tree<E>[]) new Tree[numberOfChildren];
      setKey(key);
    }

    @SafeVarargs
    public TreeNode(E key, Tree<E>... children) {
      this.children = children.clone();
      setKey(key);
    }

    @Override
    public int getNumberOfChildren() {
      return children.length;
    }

    @Override
    public Tree<E> getChild(int childIndex) {
      return children[childIndex];
    }

    @Override
    public void setChild(int childIndex, Tree<E> child) {
      children[childIndex] = child;
    }

  }

  public static class LeafNode<E> extends Tree<E> {

    public LeafNode() {}

    public LeafNode(E key) {
      setKey(key);
    }

    @Override
    public int getNumberOfChildren() {
      return 0;
    }

    @Override
    public Tree<E> getChild(int childIndex) {
      return null;
    }

    @Override
    public void setChild(int childIndex, Tree<E> child) {}

  }

  public static class BinaryNode<E> extends Tree<E> {

    private Tree<E> leftChild;
    private Tree<E> rightChild;

    public BinaryNode() {}

    public BinaryNode(E key) {
      setKey(key);
    }

    public BinaryNode(E key, Tree<E> leftChild, Tree<E> rightChild) {
      this.leftChild = leftChild;
      this.rightChild = rightChild;
      setKey(key);
    }

    @Override
    public int getNumberOfChildren() {
      return 2;
    }

    @Override
    public Tree<E> getChild(int childIndex) {
      return switch (childIndex) {
        case 0 -> leftChild;
        case 1 -> rightChild;
        default -> null;
      };
    }

    @Override
    public void setChild(int childIndex, Tree<E> child) {
      switch (childIndex) {
        case 0 -> leftChild = child;
        case 1 -> rightChild = child;
        default -> Utils.pass();
      }
    }

  }

}
