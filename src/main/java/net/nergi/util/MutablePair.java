package net.nergi.util;

import java.util.Objects;

/**
 * A mutable 2-tuple. Can be used with all mutable / immutable classes.
 *
 * @param <F> Type of first item in pair
 * @param <S> Type of second item in pair
 */
public class MutablePair<F, S> {

  private F first;
  private S second;

  /**
   * Make a new mutable pair with the given starting items.
   *
   * @param first  First item in pair
   * @param second Second item in pair
   */
  public MutablePair(F first, S second) {
    this.first = first;
    this.second = second;
  }

  public F getFirst() {
    return first;
  }

  public void setFirst(F first) {
    this.first = first;
  }

  public S getSecond() {
    return second;
  }

  public void setSecond(S second) {
    this.second = second;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MutablePair<?, ?> that = (MutablePair<?, ?>) o;
    return Objects.equals(first, that.first) && Objects
        .equals(second, that.second);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getClass(), first, second);
  }
}
