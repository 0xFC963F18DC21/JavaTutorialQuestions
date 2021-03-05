package net.nergi.util;

import java.util.Objects;

/**
 * A simple immutable pair / 2-tuple class. It is safe to put a mutable class reference in this
 * pair class.
 *
 * @param <F> Type of first item in pair
 * @param <S> Type of second item in pair
 */
public class Pair<F, S> {

  public final F first;
  public final S second;

  /**
   * Make a new pair with the given items.
   *
   * @param first  First item in pair
   * @param second Second item in pair
   */
  public Pair(F first, S second) {
    this.first = first;
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

    Pair<?, ?> pair = (Pair<?, ?>) o;
    return Objects.equals(first, pair.first) && Objects
        .equals(second, pair.second);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getClass(), first, second);
  }
}
