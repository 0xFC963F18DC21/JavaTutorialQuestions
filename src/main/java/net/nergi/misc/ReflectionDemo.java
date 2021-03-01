package net.nergi.misc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * A demonstration of the reflection capabilities of the JVM. It showcases both useful use-cases
 * and useful yet potentially dangerous use-cases. See the source code for more details.
 */
@SuppressWarnings("unused")
public class ReflectionDemo implements Runnable {

  @Override
  public void run() {
    // A good use-case of reflection is to ask the JVM's current class loader what classes it has
    // loaded into the runtime classpath.
    // You can get the object representation of a class', well, type by its fully-qualified name.
    try {
      System.out.println(Class.forName("net.nergi.misc.ReflectionDemo.AClass"));
      System.out.println(Class.forName("net.nergi.misc.ReflectionDemo.AnotherClass"));
      System.out.println(Class.forName("i.am.hopefully.an.InvalidClass"));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    // Related to the above, you can use that to dynamically load classes by name during runtime.
    // For example, this solution project uses it to run different solution classes.
    // Another example is if you had a list of (fully-qualified) class names to load, say a list of
    // gamemode classes for a game, you can load all of those during runtime, create their specific
    // instances and use them.
    // You can create an object from its class representation as follows:
    try {
      final Class<?> aClassRepresentation = Class.forName("net.nergi.misc.ReflectionDemo.AClass");

      final Constructor<?> aClassConstructor = aClassRepresentation.getDeclaredConstructor();

      final Object aClassInstance = aClassConstructor.newInstance();

      // Of course, for this to be useful, making the classes you want to load have a common
      // superclass / interface will make this a lot easier.
      if (aClassInstance instanceof AClass) {
        ((AClass) aClassInstance).doSomething();
      }
    } catch (ClassNotFoundException
        | NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException
        | InstantiationException e) {
      e.printStackTrace();
    }

    // Another (potentially dangerous, yet) useful usecase for reflection is if you're working with
    // someone's ancient API, and you find that to do something, you need to access something not
    // exposed by the API in order to query / update it. For example, I've had to do that with a
    // game I was modding in order to get some graphical / gameplay modifications to work without
    // tampering the original (open) source code for the game (as I wanted my modifications to be
    // drop-in and compatible with any other mod someone else decides to create).
    try {
      final SomeClass instance = new SomeClass(64);

      final Field someClassValueField = SomeClass.class.getDeclaredField("value");
      someClassValueField.setAccessible(true);

      final int exposedValue = someClassValueField.getInt(instance);
      System.out.println(exposedValue);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private static class AClass {

    public void doSomething() {
      System.out.println("Hello there!");
    }
  }

  private static class AnotherClass {}

  private static class SomeClass {

    private int value;

    public SomeClass(int value) {
      this.value = value;
    }
  }
}
