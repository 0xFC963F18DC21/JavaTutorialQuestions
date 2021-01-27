package net.nergi.misc;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/** A demo class to demonstrate the functional interfaces exposed by {@link Optional}. */
@SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType"})
public class OptionalPropagation implements Runnable {

  @Override
  public void run() {
    // If all valid divisions, optional returned normally.
    System.out.println(optionalDivide(optionalDivide(optionalDivide(Optional.of(48), 2), 3), 4));

    // If any divisions are zero, return nothing.
    System.out.println(optionalDivide(optionalDivide(optionalDivide(Optional.of(48), 2), 0), 4));
  }

  private static Optional<Integer> optionalDivide(Optional<Integer> number, int divisor) {
    if (divisor == 0) {
      return Optional.empty();
    } else {
      return number.map((n) -> n / divisor);
    }
  }

  /** Maps the function to the item inside the Optional if it exists. */
  public static <T, R> Optional<? extends R> fmap(
      Function<? super T, ? extends R> func, Optional<T> optional) {
    return optional.map(func);
  }

  /** Makes a pure instance of an Optional. */
  public static <T> Optional<T> pure(T item) {
    return Optional.of(item);
  }

  /** Applies a function onto two optionals. */
  public static <T, U, R> Optional<? extends R> liftA2(
      BiFunction<? super T, ? super U, ? extends R> func, Optional<T> ot, Optional<U> ou) {
    if (ot.isPresent() && ou.isPresent()) {
      return pure(func.apply(ot.get(), ou.get()));
    } else {
      return Optional.empty();
    }
  }

  /** Allows chaining of Optional functions. */
  public static <T, R> Optional<? extends R> bind(
      Optional<T> ot, Function<? super T, Optional<? extends R>> func) {
    return ot.flatMap(func);
  }

  /** Discard the previous Optional. */
  public static <T, U> Optional<U> next(Optional<T> ot, Optional<U> ou) {
    return ou;
  }

  /** Wraps an item in an Optional. */
  public static <T> Optional<T> wrapReturn(T item) {
    return pure(item);
  }
}
