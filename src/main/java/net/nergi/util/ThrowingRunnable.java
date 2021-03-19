package net.nergi.util;

/**
 * An {@link Runnable} derivative that is allowed to throw exceptions. Lambdas that can be used to
 * form normal {@link Runnable}s can also be used to create {@link ThrowingRunnable}s.
 */
public interface ThrowingRunnable<T extends Throwable> {

  /**
   * Run this {@link ThrowingRunnable}'s method, where any exceptions thrown inside are propagated
   * outwards.
   *
   * @throws T Any exception thrown inside.
   */
  void run() throws T;

  /**
   * Convert this {@link ThrowingRunnable} to a normal {@link Runnable} that throws an unchecked
   * {@link FallThroughException} if an exception is thrown while running it.
   *
   * @return A {@link Runnable} that throws unchecked exceptions.
   */
  default Runnable toRunnable() {
    return () -> {
      try {
        run();
      } catch (Throwable t) {
        throw new FallThroughException(t);
      }
    };
  }
}
