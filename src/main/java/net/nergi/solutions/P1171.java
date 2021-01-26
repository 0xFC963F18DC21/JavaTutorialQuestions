package net.nergi.solutions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P1171 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "1171: Cloning graphs";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    final GraphNode<Integer> rootNode = new GraphNode<>();
    rootNode.setKey(1);

    final GraphNode<Integer> twoNode = new GraphNode<>();
    twoNode.setKey(2);

    final GraphNode<Integer> tailNode = new GraphNode<>();
    tailNode.setKey(3);

    rootNode.addSuccessor(twoNode);
    twoNode.addSuccessor(tailNode);
    tailNode.addSuccessor(rootNode);

    final GraphNode<Integer> clonedNode = rootNode.clone();

    System.out.println(rootNode.getKey() + " and " + clonedNode.getKey());
    System.out.println(
        rootNode.getSuccessor(0).getKey() + " and " + clonedNode.getSuccessor(0).getKey());
    System.out.println(
        rootNode.getSuccessor(0).getSuccessor(0).getKey()
            + " and "
            + clonedNode.getSuccessor(0).getSuccessor(0).getKey());
  }

  public static class GraphNode<E> implements Cloneable {

    private final List<GraphNode<E>> successors;
    private E key;

    public GraphNode() {
      successors = new ArrayList<>();
    }

    public GraphNode(E key) {
      this();
      setKey(key);
    }

    public int getNumberOfSuccessors() {
      return successors.size();
    }

    public GraphNode<E> getSuccessor(int successorIndex) {
      return successors.get(successorIndex);
    }

    public void addSuccessor(GraphNode<E> successor) {
      successors.add(successor);
    }

    public E getKey() {
      return key;
    }

    public void setKey(E key) {
      this.key = key;
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public GraphNode<E> clone() {
      return cloneHelper(new HashSet<>(Set.of(this)));
    }

    private GraphNode<E> cloneHelper(Set<GraphNode<E>> visited) {
      final GraphNode<E> clone = new GraphNode<>();
      clone.setKey(key);

      for (int i = 0; i < getNumberOfSuccessors(); ++i) {
        final GraphNode<E> succ = getSuccessor(i);
        if (visited.contains(succ)) {
          clone.addSuccessor(succ);
        } else {
          visited.add(succ);
          clone.addSuccessor(succ.cloneHelper(new HashSet<>(visited)));
        }
      }

      return clone;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder(key.toString()).append(" -> (");
      for (GraphNode<E> succ : successors) {
        sb.append(succ.getKey()).append(", ");
      }

      if (getNumberOfSuccessors() > 0) {
        sb.setLength(sb.length() - 2); // Cut off trailing ", "
      }

      sb.append(")");

      return sb.toString();
    }
  }
}
