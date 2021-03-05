package net.nergi.util;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Class that holds different methods for functional
 */
@SuppressWarnings("DeprecatedIsStillUsed")
public final class FunctionalModifiers {

  /** Hidden as this is a library class. */
  private FunctionalModifiers() {}

  /**
   * Runs a block. If an exception occurs, return the default value. Else return the calculated
   * value from the block. Deprecated; replace with an {@link ExceptionSuppressor} instance.
   *
   * @param callable     Code block to execute
   * @param defaultValue Default value if an exception occurs
   * @param <T>          Return type
   * @return Returned value from block or default value if an exception occurs
   */
  @Deprecated(since = "2021/03/03")
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

  /**
   * Curry the {@link BiFunction}, turning the two-argument function into a single-argument
   * function that when, supplied with an argument, returns another function which takes a second
   * argument in order to get the desired result.
   *
   * @param function Function to curry
   * @param <T>      Type of first argument
   * @param <U>      Type of second argument
   * @param <R>      Return type
   * @return Function representing the curried function.
   */
  public static <T, U, R> Function<T, Function<U, R>> curry(
      BiFunction<? super T, ? super U, ? extends R> function) {
    return t -> partialApply(function, t);
  }

  /**
   * Uncurries a function to form a {@link BiFunction}.
   *
   * @param function Curried function to uncurry
   * @param <T>      Type of first argument
   * @param <U>      Type of second argument
   * @param <R>      Return type
   * @return Uncurried BiFunction.
   */
  public static <T, U, R> BiFunction<T, U, R> uncurry(
      Function<? super T, Function<? super U, ? extends R>> function) {
    return (t, u) -> function.apply(t).apply(u);
  }

  /**
   * Flip the argument order in a {@link BiFunction}.
   *
   * @param function {@link BiFunction} to flip
   * @param <T>      Type of first argument
   * @param <U>      Type of second argument
   * @param <R>      Return type
   * @return Flipped function.
   */
  public static <T, U, R> BiFunction<U, T, R> flip(
      BiFunction<? super T, ? super U, ? extends R> function) {
    return (u, t) -> function.apply(t, u);
  }
}
