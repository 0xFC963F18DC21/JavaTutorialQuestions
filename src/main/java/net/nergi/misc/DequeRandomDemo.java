package net.nergi.misc;

import net.nergi.misc.objects.DequeRandom;

/** Demo class for {@link DequeRandom}. */
@SuppressWarnings("unused")
public class DequeRandomDemo implements Runnable {

  @Override
  public void run() {
    // Make random generator
    final DequeRandom drnd = new DequeRandom(4);

    // Output some numbers
    System.out.println("16 random numbers from 0 to 6:");
    for (int i = 0; i < 16; ++i) {
      System.out.print(drnd.nextInt(7) + " ");
    }

    System.out.print("\n");
  }
}
