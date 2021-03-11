package net.nergi.util.functional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

/**
 * A two-state class. Typically used in functional programming for handling exceptions by
 * separating which class instance holds what.
 *
 * <p>Typically, Right is used to hold the result of successful operations, and Left is used to
 * hold exceptions or other errors that occur during an operation.
 *
 * @param <L> Type held by Left instance.
 * @param <R> Type held by Right instance.
 */
public abstract class Either<L, R> implements Functor<R> {

  /**
   * Make a new Left instance with the given item held inside.
   *
   * @param item Item held in Left instance
   * @param <L>  Type of item held by Left instance
   * @param <R>  The potential type of the Right instance
   * @return A Left-Either instance.
   */
  public static <L, R> Either<L, R> leftFrom(L item) {
    return new Left<>(item);
  }

  /**
   * Make a new Right instance with the given item held inside.
   *
   * @param item Item held in Right instance
   * @param <L>  The potential type of the Left instance
   * @param <R>  Type of item held by Right instance
   * @return A Right-Either instance.
   */

  public static <L, R> Either<L, R> rightFrom(R item) {
    return new Right<>(item);
  }

  /**
   * Is this a Left-Either instance?
   *
   * @return Whether this is a Left-Either instance.
   */
  public boolean isLeft() {
    return this instanceof Left;
  }

  /**
   * Is this a Right-Either instance?
   *
   * @return Whether this is a Right-Either instance.
   */
  public boolean isRight() {
    return this instanceof Right;
  }

  /**
   * Attempts to get the Left value. If this is not a Left-Either instance, the operation will
   * throw a {@link NoSuchElementException}.
   *
   * @return The Left value, if applicable.
   */
  public L fromLeft() {
    if (isLeft()) {
      return ((Left<L, R>) this).getItem();
    }

    throw new NoSuchElementException("Not a Left.");
  }

  /**
   * Attempts to get the Right value. If this is not a Right-Either instance, the operation will
   * throw a {@link NoSuchElementException}.
   *
   * @return The Right value, if applicable.
   */
  public R fromRight() {
    if (isRight()) {
      return ((Right<L, R>) this).getItem();
    }

    throw new NoSuchElementException("Not a Right.");
  }

  /**
   * Applies a mapping function to the contents of a Right-Either.
   * Leaves a Left-Either alone.
   *
   * @param function Mapping function
   * @param <U>      Output type
   * @return A (possibly) mapped Either.
   */
  @Override
  public <U> Functor<U> fmap(Function<? super R, ? extends U> function) {
    if (isRight()) {
      try {
        return rightFrom(function.apply(fromRight()));
      } catch (Exception e) {
        return leftFrom(e);
      }
    } else {
      return leftFrom(fromLeft());
    }
  }

  /**
   * Replace the contents of this Either instance only if it is a Right-Either.
   *
   * @param item New item
   * @param <U>  Type of the new item
   * @return A (possibly) replaced Either.
   */
  @Override
  public <U> Functor<U> replace(U item) {
    if (isRight()) {
      return rightFrom(item);
    } else {
      return leftFrom(fromLeft());
    }
  }

  // Left-Either instance
  private static class Left<L, R> extends Either<L, R> {

    private final L item;

    public Left(L item) {
      this.item = item;
    }

    public L getItem() {
      return item;
    }

    @Override
    public String toString() {
      return "Left (" + item + ")";
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Left<?, ?> left = (Left<?, ?>) o;
      return Objects.equals(item, left.item);
    }

    @Override
    public int hashCode() {
      return Objects.hash(getClass(), item);
    }
  }

  // Right-Either instance
  private static class Right<L, R> extends Either<L, R> {

    private final R item;

    public Right(R item) {
      this.item = item;
    }

    public R getItem() {
      return item;
    }

    @Override
    public String toString() {
      return "Right (" + item + ")";
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Right<?, ?> right = (Right<?, ?>) o;
      return Objects.equals(item, right.item);
    }

    @Override
    public int hashCode() {
      return Objects.hash(getClass(), item);
    }
  }
}

