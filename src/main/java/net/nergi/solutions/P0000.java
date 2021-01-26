package net.nergi.solutions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import net.nergi.Main;
import net.nergi.Solution;

/**
 * This class runs any class defined in net.nergi.misc.
 * All classes defined there must implement {@link Runnable}.
 */
@SuppressWarnings("unused")
public class P0000 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "0000: Arbitrary Class Executor";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    // If no arguments for class name is given, then exit with message.
    if (Main.otherArgs.isEmpty()) {
      System.out.println("Please give a class name to execute.");
      return;
    }

    try {
      // Attempt to get class from misc package.
      final Class<?> clazz = Class.forName("net.nergi.misc." + Main.otherArgs.get(0));
      final Constructor<?> ctor = clazz.getConstructor();

      final Object rClass = ctor.newInstance();
      if (rClass instanceof Runnable) {
        ((Runnable) rClass).run();
      }
    } catch (ClassNotFoundException e) {
      // This error catches any typing errors or erroneous class names.
      System.out.println("Invalid class name! [" + Main.otherArgs.get(0) + "]");
    } catch (NoSuchMethodException
        | IllegalAccessException
        | InstantiationException
        | InvocationTargetException e) {
      // For all other reflection-related exceptions, its stack trace is printed along with a
      // warning message.
      System.out.println("*** REFLECTION EXCEPTION ***");
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("*** MISCELLANEOUS EXCEPTION ***");
      e.printStackTrace();
    }
  }

}
