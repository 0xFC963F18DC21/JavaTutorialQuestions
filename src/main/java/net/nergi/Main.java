package net.nergi;

import java.lang.reflect.*;

public class Main {

  public static String secondArg = "";

  public static void main(String[] args) {
    if (args.length >= 1) {
      System.out.println("Received problem input: " + args[0]);

      if (args.length >= 2) {
        System.out.println("Received second arg: " + args[1]);
        secondArg = args[1];
      }
    } else {
      System.out.println("Please give the problem hex string as an argument.");
      return;
    }

    try {
      Class<?> clazz = Class.forName("net.nergi.solutions.P" + args[0]);
      Constructor<?> ctor = clazz.getConstructor();

      Object solClass = ctor.newInstance();
      if (solClass instanceof Solution) {
        System.out.println("-----------------------------------------");
        System.out.println(((Solution) solClass).getName());
        System.out.println("-----------------------------------------");

        ((Solution) solClass).exec();
      }
    } catch (ClassNotFoundException e) {
      System.out.println("Invalid problem hex string! [" + args[0] + "]");
    } catch (NoSuchMethodException
        | IllegalAccessException
        | InstantiationException
        | InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
