package net.nergi.util;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.WeakHashMap;
import net.nergi.util.lazy.LazyReference;

/**
 * A truly immutable pair type that re-uses old instances if they are identical (i.e. the objects
 * inside are exactly the same). This pair is only safe to be used with truly immutable classes that
 * implement <code>.equals()</code> and <code>.hashCode()</code>.
 *
 * @param <F> Type of first item in pair
 * @param <S> Type of second item in pair
 */
@SuppressWarnings("unchecked")
public class ImmutablePair<F, S> {

  private static final LazyReference<
      WeakHashMap<
          ImmutablePair<?, ?>, WeakReference<ImmutablePair<?, ?>>>>
      cached = LazyReference.fromSupplier(WeakHashMap::new);

  public final F first;
  public final S second;

  private ImmutablePair(F first, S second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Make a new pair from the given objects.
   *
   * @param first  First item in pair
   * @param second Second item in pair
   * @param <F>    Type of first item in pair
   * @param <S>    Type of second item in pair
   * @return A pair formed from these objects. If the exact same objects have been used before, an
   * old pair instance is returned.
   */
  public static <F, S> ImmutablePair<F, S> makePair(F first, S second) {
    final ImmutablePair<F, S> p = new ImmutablePair<>(first, second);
    if (cached.getValue().containsKey(p)) {
      return (ImmutablePair<F, S>) cached.getValue().get(p).get();
    } else {
      cached.getValue().put(p, new WeakReference<>(p));
      return p;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ImmutablePair<?, ?> that = (ImmutablePair<?, ?>) o;
    return Objects.equals(first, that.first) && Objects
        .equals(second, that.second);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getClass(), first, second);
  }
}
