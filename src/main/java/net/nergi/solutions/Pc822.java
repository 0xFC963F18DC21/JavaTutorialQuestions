package net.nergi.solutions;

import net.nergi.Solution;
import net.nergi.solutions.P7041.TreeNode;

/** Solution for c822. */
@SuppressWarnings("unused")
public class Pc822 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "c822: Problems cloning tree nodes";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    try {
      final TreeNode<String> n1 = new TreeNode<>(1);
      n1.setKey("A");
      final TreeNode<String> n2 = new TreeNode<>(1);
      n2.setKey("B");
      n1.setChild(0, n2);
      n2.setChild(0, n1);

      final TreeNode<String> c1 = n1.clone();
      System.out.println("Clone successful :D");
    } catch (CloneNotSupportedException e) {
      System.out.println("Clone failed :(");
    } catch (Exception e) {
      System.out.println("*** OTHER EXCEPTION ***");
      e.printStackTrace();
    }

    try {
      TreeNode<String> n3 = new TreeNode<>(2);
      n3.setKey("C");
      TreeNode<String> n4 = new TreeNode<>(1);
      n4.setKey("D");
      TreeNode<String> n5 = new TreeNode<>(0);
      n5.setKey("E");
      n3.setChild(0, n4);
      n3.setChild(1, n5);
      n4.setChild(0, n5);

      final TreeNode<String> c2 = n3.clone();
      System.out.println("Clone successful :D");
    } catch (CloneNotSupportedException e) {
      System.out.println("Clone failed :(");
    } catch (Exception e) {
      System.out.println("*** OTHER EXCEPTION ***");
      e.printStackTrace();
    }
  }
}
