package net.nergi.solutions;

import java.util.ArrayDeque;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P7041 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "7041: Cloning tree nodes";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    final TreeNode<Integer> testNode = new TreeNode<>(2);
    testNode.setKey(0);

    final TreeNode<Integer> ln = new TreeNode<>(0);
    ln.setKey(1);

    final TreeNode<Integer> rn = new TreeNode<>(0);
    rn.setKey(2);

    testNode.setChild(0, ln);
    testNode.setChild(1, rn);

    try {
      final var clonedNode = testNode.clone();
      System.out.println("Clone successful :D");
    } catch (CloneNotSupportedException e) {
      System.out.println("Clone failed :(");
    }
  }

  public static class TreeNode<E> implements Cloneable {

    private final TreeNode<E>[] children;
    private E key;

    @SuppressWarnings("unchecked")
    public TreeNode(int numberOfChildren) {
      children = (TreeNode<E>[]) new TreeNode[numberOfChildren];
    }

    public int getNumberOfChildren() {
      return children.length;
    }

    public TreeNode<E> getChild(int childIndex) {
      return children[childIndex];
    }

    // We don't want cycles in our trees, as trees *must* be acyclic.
    public void setChild(int childIndex, TreeNode<E> child) throws IllegalArgumentException {
      final IllegalArgumentException potentialException =
          new IllegalArgumentException("Trees must not contain cycles!");

      // Prevent direct and indirect cycles in a tree.
      final ArrayDeque<TreeNode<E>> toVisit = new ArrayDeque<>();
      toVisit.add(child);

      while (!toVisit.isEmpty()) {
        final var t = toVisit.pop();

        if (t == this) {
          throw potentialException;
        }

        for (int i = 0; i < t.getNumberOfChildren(); ++i) {
          final var cc = t.getChild(i);
          if (cc != null) {
            toVisit.add(cc);
          }
        }
      }

      children[childIndex] = child;
    }

    public E getKey() {
      return key;
    }

    public void setKey(E key) {
      this.key = key;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TreeNode<E> clone() throws CloneNotSupportedException {
      final TreeNode<E> clone = (TreeNode<E>) super.clone();

      clone.setKey(key);
      for (int i = 0; i < getNumberOfChildren(); ++i) {
        clone.setChild(i, getChild(i).clone());
      }

      return clone;
    }
  }
}
