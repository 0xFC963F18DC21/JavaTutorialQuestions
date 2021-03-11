package net.nergi.misc;

import java.util.function.UnaryOperator;
import net.nergi.util.functional.Either;
import net.nergi.util.functional.Functor;

/** Demonstration of the error-propagation of the Either type. */
@SuppressWarnings("unused")
public class EitherPropagation implements Runnable {

  @Override
  public void run() {
    // Testing functions.
    final UnaryOperator<Integer> halve = i -> i / 2;

    @SuppressWarnings("divzero")
    final UnaryOperator<Integer> error = i -> i / 0;

    System.out.println("A successful chain of operations might look like this:");

    Functor<Integer> successfulEither = Either.rightFrom(16);
    System.out.println(successfulEither);

    successfulEither = successfulEither.fmap(halve);
    System.out.println(successfulEither);

    successfulEither = successfulEither.fmap(halve);
    System.out.println(successfulEither);

    successfulEither = successfulEither.fmap(halve);
    System.out.println(successfulEither);

    System.out.println("An unsuccessful chain of operations might look like this:");

    Functor<Integer> errorEither = Either.rightFrom(64);
    System.out.println(errorEither);

    errorEither = errorEither.fmap(halve);
    System.out.println(errorEither);

    errorEither = errorEither.fmap(error);
    System.out.println(errorEither);

    errorEither = errorEither.fmap(halve);
    System.out.println(errorEither);
  }
}
