package net.nergi.solutions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import net.nergi.Main;
import net.nergi.Solution;
import net.nergi.util.Utils;

/**
 * This class runs any class defined in {@link net.nergi.misc} All classes defined there must
 * implement {@link Runnable} as they need to effectively act like main classes.
 */
@SuppressWarnings("unused")
public class P0000 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "0000: Arbitrary Class Executor";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // If no arguments for class name is given, then exit with message.
    if (Main.OTHER_ARGS.isEmpty()) {
      System.out.println("Please give a class name to execute.");
      return;
    }

    try {
      // Attempt to get class from misc package.
      final Class<?> clazz = Class.forName("net.nergi.misc." + Main.OTHER_ARGS.get(0));
      final Constructor<?> ctor = clazz.getConstructor();

      final Object rClass = ctor.newInstance();
      if (rClass instanceof Runnable) {
        Main.OTHER_ARGS.remove(0);

        final String name = rClass.getClass()
            .getName();
        final int last = name.lastIndexOf('.');

        System.out.println(name.substring(last == -1 ? 0 : last + 1));
        Utils.printSeparator();

        ((Runnable) rClass).run();
      }
    } catch (ClassNotFoundException e) {
      // This error catches any typing errors or erroneous class names.
      System.out.println("Invalid class name! [" + Main.OTHER_ARGS.get(0) + "]");
      System.out.println("Define or put classes in the package net.nergi.misc to use them here!");
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
