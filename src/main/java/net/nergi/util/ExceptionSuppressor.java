package net.nergi.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * A block runner that suppresses and collects thrown exceptions. If it fails to successfully
 * catch an exception, it will rethrow the exception, nested within a {@link RuntimeException}.
 */
public final class ExceptionSuppressor {

  /** All collected exceptions. */
  private final Set<Throwable> suppressed = new HashSet<>();

  /**
   * Filter on which exception superclasses are suppressed. The filter also catches subclasses of
   * the given exception classes.
   */
  private final Set<Class<? extends Throwable>> filter = new HashSet<>();

  /**
   * Make a new universal suppressor.
   *
   * <p><strong>Warning</strong>: this will catch anything and everything, including
   * subclasses of {@link Error}. So it might be better to use a filtered instance where one is
   * paranoid that errors can occur.
   */
  public ExceptionSuppressor() {
    filter.add(Throwable.class);
  }

  /**
   * Make a suppressor that catches the given exception classes and their subclasses.
   *
   * @param toCatch Exception superclasses
   */
  public ExceptionSuppressor(Class<? extends Throwable>... toCatch) {
    filter.addAll(Arrays.asList(toCatch));
  }

  /**
   * Make a suppressor that catches the given exception classes and their subclasses.
   *
   * @param toCatch Exception superclasses
   */
  public ExceptionSuppressor(Collection<Class<? extends Throwable>> toCatch) {
    filter.addAll(toCatch);
  }

  private boolean shouldCatch(Throwable t) {
    return filter.stream().anyMatch(c -> c.isAssignableFrom(t.getClass()));
  }

  /**
   * Run an action with this expression suppressor.
   *
   * @param action Action to run
   */
  public void suppressedExecute(Runnable action) {
    try {
      action.run();
    } catch (Throwable t) {
      if (shouldCatch(t)) {
        suppressed.add(t);
      } else {
        throw new RuntimeException(t);
      }
    }
  }

  /**
   * Run a callable and attempt to get its result with this exception suppressor. If an exception
   * was suppressed, a default value is returned instead.
   *
   * @param action       Callable to execute
   * @param defaultValue Default value to return
   * @param <T>          Type of result
   * @return Result or default value.
   */
  public <T> T suppressedSubmit(Callable<T> action, T defaultValue) {
    try {
      return action.call();
    } catch (Throwable t) {
      if (shouldCatch(t)) {
        suppressed.add(t);
      } else {
        throw new RuntimeException(t);
      }

      return defaultValue;
    }
  }

  /**
   * Get a collection of all of the suppressed exceptions.
   *
   * @return All suppressed exceptions.
   */
  public Set<Throwable> getSuppressed() {
    return new HashSet<>(suppressed);
  }

  /**
   * Get the number of suppressed exceptions.
   *
   * @return The number of suppressed exceptions.
   */
  public int getSuppressedCount() {
    return suppressed.size();
  }

  /**
   * Checks if any exceptions were suppressed.
   *
   * @return <code>true</code> if no exceptions were suppressed, <code>false</code> otherwise.
   */
  public boolean isCleanExecution() {
    return suppressed.isEmpty();
  }
}