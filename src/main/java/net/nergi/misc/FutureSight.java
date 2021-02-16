package net.nergi.misc;

import java.util.List;
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

    final var result = numberOfOdds(stupidlyLargeList);
    System.out.printf("There are %d odd numbers in the large list.%n",
        FunctionalModifiers.exceptionCoalesce(result::get, -1L));

    // We can see that the get blocks until the task finishes.
    // We can use this with multiple futures to wait for multiple tasks to finish.
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
        () -> ints.stream().filter(i -> i % 2 == 1).count()
    );
  }
}
