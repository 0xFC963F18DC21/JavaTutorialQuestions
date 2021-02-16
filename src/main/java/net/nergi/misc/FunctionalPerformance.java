package net.nergi.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Demo class showing the upsides and downsides of using the functional interface. For more
 * information, see the source code.
 */
@SuppressWarnings("unused")
public class FunctionalPerformance implements Runnable {

  @Override
  public void run() {
    // --- Upsides ---

    // When constructing a list from a range of integers, it can be useful to transform a stream
    // of integers instead, as this gives much shorter code.

    System.out.println("List construction (+)\n");

    // CONSTRUCT FUNCTIONALLY
    final List<List<Integer>> intsSquaresCubesFunc = IntStream.rangeClosed(1, 10)
        .boxed()
        .map(i -> List.of(i, i * i, i * i * i))
        .collect(Collectors.toList()); // This can be one-lined / condensed
    // ^ The stream can also be transformed further instead of collected if one wants to do so.

    // CONSTRUCT VIA FOR LOOP
    final List<List<Integer>> intsSquaresCubesIter = new ArrayList<>();
    for (int i = 1; i <= 10; ++i) {
      intsSquaresCubesIter.add(List.of(i, i * i, i * i * i));
    } // This... can't really be condensed further

    System.out.println(intsSquaresCubesFunc);
    System.out.println(intsSquaresCubesIter);
    System.out.println(intsSquaresCubesFunc.equals(intsSquaresCubesIter));

    // --- Downsides ---

    // Nesting large streams can be memory-expensive on the heap.

    System.out.println("\nStream Memory Usage (-)\n");

    // Suggest the system to GC, to clear the usage of the last function.
    System.gc();
    try {
      Thread.sleep(4000);
      System.out.println("STREAM");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Streams
    IntStream.range(0, 100000).forEach(
        i -> IntStream.range(0, 100000).forEach(
            j -> {
              if (i % 10000 == 0 && j == 50000) {
                System.out.println("Used memory: " + getHeapUsageBytes());
              }
            }
        )
    );

    // Suggest the system to GC, to clear the usage of the last function.
    System.gc();
    try {
      Thread.sleep(4000);
      System.out.println("LOOP");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // For loops - note that we can use primitives
    for (int i = 0; i < 100000; i++) {
      for (int j = 0; j < 100000; j++) {
        if (i % 10000 == 0 && j == 50000) {
          System.out.println("Used memory: " + getHeapUsageBytes());
        }
      }
    }
  }

  // Returns the amount of used bytes in the heap.
  private static long getHeapUsageBytes() {
    final Runtime current = Runtime.getRuntime();
    return current.totalMemory() - current.freeMemory();
  }
}
