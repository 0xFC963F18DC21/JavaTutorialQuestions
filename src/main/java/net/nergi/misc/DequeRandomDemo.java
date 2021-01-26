package net.nergi.misc;

import net.nergi.misc.objects.DequeRandom;

/** Demo class for {@link DequeRandom}. Prints 24 numbers. */
@SuppressWarnings("unused")
public class DequeRandomDemo implements Runnable {

  @Override
  public void run() {
    // Make random generator
    final DequeRandom drnd = new DequeRandom(4);

    // Output some numbers
    System.out.println("24 random numbers from 0 to 6:");
    for (int i = 0; i < 24; ++i) {
      System.out.print(drnd.nextInt(7) + " ");
    }

    System.out.print("\n");
  }
}
