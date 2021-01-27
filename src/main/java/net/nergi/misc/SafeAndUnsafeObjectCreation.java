package net.nergi.misc;

import java.lang.reflect.Constructor;
import net.nergi.Utils;
import net.nergi.misc.objects.GameItem;

@SuppressWarnings("unused")
public class SafeAndUnsafeObjectCreation implements Runnable {

  @Override
  public void run() {
    // The context of this demo class is to show the differences between safe initialisation
    // of classes using their intended methods vs forcing creation using their constructors.

    // Safe initialisation:
    final GameItem item1 = GameItem.makeItem("Sword", "A generic western sword.");
    final GameItem item2 = GameItem.makeItem("Sword", "A generic western sword.");

    // The following showcases what could happen if one decides to break encapsulation of an
    // object and decide to force usage of functionality that wasn't exposed publicly.

    // Unsafe (memory-greedy) initialisation:
    final GameItem item3;
    try {
      final Class<GameItem> gameItemClass = GameItem.class;
      final Constructor<GameItem> gameItemConstructor =
          gameItemClass.getDeclaredConstructor(String.class, String.class);

      gameItemConstructor.setAccessible(true);

      item3 = gameItemConstructor.newInstance("Sword", "A generic western sword.");
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    // Comparison
    System.out.println(item1);
    System.out.println(item2);
    System.out.println(item3);

    Utils.printSeparator();

    System.out.printf("Item1: %s\n", System.identityHashCode(item1));
    System.out.printf("Item2: %s\n", System.identityHashCode(item2));
    System.out.printf("Item3: %s\n", System.identityHashCode(item3));

    Utils.printSeparator();

    System.out.printf("Item1.equals(Item2): %s\n", item1.equals(item2));
    System.out.printf("     Item1 == Item2: %s\n", item1 == item2);

    Utils.printSeparator();

    System.out.printf("Item1.equals(Item3): %s\n", item1.equals(item3));
    System.out.printf("     Item1 == Item3: %s\n", item1 == item3);
  }
}
