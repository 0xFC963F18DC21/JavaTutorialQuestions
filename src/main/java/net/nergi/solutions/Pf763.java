package net.nergi.solutions;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;
import net.nergi.Solution;
import net.nergi.solutions.P1171.GraphNode;

@SuppressWarnings("unused")
public class Pf763 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "f763: Simulating garbage collection";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // Make nodes
    final GraphNode<Integer> node0 = new GraphNode<>(0);
    final GraphNode<Integer> node1 = new GraphNode<>(1);
    final GraphNode<Integer> node2 = new GraphNode<>(2);
    final GraphNode<Integer> node3 = new GraphNode<>(3);
    final GraphNode<Integer> node4 = new GraphNode<>(4);
    final GraphNode<Integer> node5 = new GraphNode<>(5);
    final GraphNode<Integer> node6 = new GraphNode<>(6);
    final GraphNode<Integer> node7 = new GraphNode<>(7);
    final GraphNode<Integer> node8 = new GraphNode<>(8);
    final GraphNode<Integer> node9 = new GraphNode<>(9);

    node0.addSuccessor(node1);
    node0.addSuccessor(node3);

    node5.addSuccessor(node6);
    node5.addSuccessor(node8);

    node6.addSuccessor(node7);
    node8.addSuccessor(node9);

    // Make heap and active sets
    final Set<GraphNode<Integer>> heapMemory =
        new HashSet<>(Set.of(node0, node1, node2, node3, node4, node5, node6, node7, node8, node9));

    final Set<GraphNode<Integer>> activeNodes = new HashSet<>(Set.of(node0, node3, node5, node9));

    // Make GC method
    final Runnable garbageCollector =
        () -> {
          final Set<GraphNode<Integer>> nonDanglingReferences = new HashSet<>();

          for (GraphNode<Integer> node : activeNodes) {
            final ArrayDeque<GraphNode<Integer>> toVisit = new ArrayDeque<>();
            toVisit.add(node);

            while (!toVisit.isEmpty()) {
              final var current = toVisit.pop();
              nonDanglingReferences.add(current);

              for (int i = 0; i < node.getNumberOfSuccessors(); ++i) {
                if (!nonDanglingReferences.contains(node.getSuccessor(i))) {
                  toVisit.add(node.getSuccessor(i));
                }
              }
            }
          }

          heapMemory.removeIf(node -> !nonDanglingReferences.contains(node));
        };

    final Runnable printAllNodes =
        () -> {
          for (GraphNode<Integer> node : heapMemory) {
            System.out.printf("%s%s\n", node, (activeNodes.contains(node)) ? " (ACTIVE)" : "");
          }
        };

    // Test method:
    System.out.println("Before GC:");
    printAllNodes.run();

    garbageCollector.run();

    System.out.println("----------\nAfter GC:");
    printAllNodes.run();
  }
}
