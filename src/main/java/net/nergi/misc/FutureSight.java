package net.nergi.misc;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.nergi.FunctionalModifiers;

/** A demo on {@link Future} objects. */
@SuppressWarnings("unused")
public class FutureSight implements Runnable {

  private static ExecutorService executor;

  @Override
  public void run() {
    // We can make a few large lists and try and filter through them:
    final List<Integer> stupidlyLargeList = IntStream.rangeClosed(0, 1000000)
        .boxed()
        .collect(Collectors.toList());

    final var fut1 = getService()
        .submit(
            () -> {
              final var result = numberOfOdds(stupidlyLargeList);
              final String toPrint = String.format(
                  "There are %d odd numbers in the large list.%n",
                  FunctionalModifiers.exceptionCoalesce(result::get, -1L));

              synchronized (System.out) {
                System.out.print(toPrint);
              }

              return "Odds";
            });

    final var fut2 = getService()
        .submit(
            () -> {
              final var result = numberOfSquares(stupidlyLargeList);
              final String toPrint = String.format(
                  "There are %d square numbers in the large list.%n",
                  FunctionalModifiers.exceptionCoalesce(result::get, -1L));

              synchronized (System.out) {
                System.out.print(toPrint);
              }

              return "Squares";
            });

    // We can see that the get blocks until the task finishes.
    // We can use this with multiple futures to wait for multiple tasks to finish.
    try {
      System.out.printf("Results:%n%s%n%s%n", fut1.get(), fut2.get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    System.out.println("The end of the main class.");

    // Finish using the executor
    getService().shutdown();
  }

  private static ExecutorService getService() {
    if (executor == null) {
      executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    return executor;
  }

  private Future<Long> numberOfOdds(List<Integer> ints) {
    return getService().submit(
        () -> ints.stream().filter(i -> i % 2 == 1).count());
  }

  private Future<Long> numberOfSquares(List<Integer> ints) {
    return getService().submit(
        () -> ints.stream().filter(i -> {
          final int root = (int) Math.sqrt((double) i);
          return root * root == i;
        }).count());
  }
}
