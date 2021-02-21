package net.nergi.misc.objects;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/** A block runner that suppresses and collects thrown exceptions. */
public class ExceptionSuppressor {

  /** All collected exceptions. */
  private Set<Throwable> suppressed = new HashSet<>();

  /**
   * Run an action with this expression suppressor.
   *
   * @param action Action to run
   */
  public void suppressedExecute(Runnable action) {
    try {
      action.run();
    } catch (Exception e) {
      suppressed.add(e);
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
    } catch (Exception e) {
      suppressed.add(e);
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
