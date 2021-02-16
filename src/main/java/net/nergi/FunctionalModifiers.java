package net.nergi;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Class that holds different methods for functional
 */
public final class FunctionalModifiers {

  /** Hidden as this is a library class. */
  private FunctionalModifiers() {}

  /**
   * Runs a block. If an exception occurs, return the default value. Else return the calculated
   * value from the block.
   *
   * @param callable     Code block to execute
   * @param defaultValue Default value if an exception occurs
   * @param <T>          Return type
   * @return             Returned value from block or default value if an exception occurs
   */
  public static <T> T exceptionCoalesce(Callable<T> callable, T defaultValue) {
    try {
      return callable.call();
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Partially apply a two-argument function onto a single argument. Transforms the function into
   * a function that takes one argument of the type of the old function's second argument.
   *
   * @param function Function to partially apply
   * @param arg      Argument to apply the function to
   * @param <T>      First argument type
   * @param <U>      Second argument type
   * @param <R>      Return type
   * @return Partially applied function.
   */
  public static <T, U, R> Function<U, R> partialApply(
      BiFunction<? super T, ? super U, ? extends R> function, T arg) {
    return u -> function.apply(arg, u);
  }

  /**
   * Fully apply a single-argument function onto an argument. Transforms the function into an
   * {@link Supplier} for the value it would return if it was applied normally.
   *
   * @param function Function to apply
   * @param arg      Argument to apply the function to
   * @param <T>      Argument type
   * @param <R>      Return type
   * @return Supplier for result of applying <code>function</code> to <code>arg</code>.
   */
  public static <T, R> Supplier<R> partialApply(
      Function<? super T, ? extends R> function, T arg) {
    return () -> function.apply(arg);
  }
}
