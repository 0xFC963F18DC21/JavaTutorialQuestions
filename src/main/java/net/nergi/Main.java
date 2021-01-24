package net.nergi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public final class Main {

  /**
   * Any other command-line arguments given to the program. Solutions may need these in order to
   * test them outside of the unit tests.
   */
  public static ArrayList<String> otherArgs = new ArrayList<>();

  public static void main(String[] args) {
    // The first argument expected is the hex string of the problem the user wants the solution to.
    // All other arguments are coalesced into the list otherArgs so that the solution classes can
    // use the other arguments.
    if (args.length >= 1) {
      args[0] = args[0].toLowerCase();
      System.out.println("Received problem input: " + args[0]);

      if (args.length >= 2) {
        otherArgs.addAll(Arrays.asList(args));
        otherArgs.remove(0);

        System.out.println("Received more args: " + otherArgs);
      }
    } else {
      System.out.println("Please give the problem hex string as an argument.");
      return;
    }

    try {
      // In order to support selecting different solutions as the user wishes, we will pick the
      // class to run via reflection.
      // This is why all solution classes are prefixed with P, as some hex strings are not valid
      // identifiers.
      final Class<?> clazz = Class.forName("net.nergi.solutions.P" + args[0]);
      final Constructor<?> ctor = clazz.getConstructor();

      final Object solClass = ctor.newInstance();
      if (solClass instanceof Solution) {
        System.out.println("-------------------------------------------------------------");
        System.out.println(((Solution) solClass).getName());
        System.out.println("-------------------------------------------------------------");

        ((Solution) solClass).exec();

        if (Utils.getBr() != null) {
          Utils.getBr().close();
        }
      }
    } catch (ClassNotFoundException e) {
      // This error catches any typing errors or erroneous problem hex strings.
      System.out.println("Invalid problem hex string! [" + args[0] + "]");
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
