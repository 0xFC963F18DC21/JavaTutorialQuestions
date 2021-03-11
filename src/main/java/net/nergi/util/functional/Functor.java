package net.nergi.util.functional;

import java.util.function.Function;

/**
 * An object type that can have mapping operations applied to them.
 *
 * @param <T> Type of object held in the functor.
 */
public interface Functor<T> {

  /**
   * Apply the given function to the functor's contents.
   *
   * @param function Mapping function
   * @param <U>      Output type
   * @return Mapped functor.
   */
  <U> Functor<U> fmap(Function<? super T, ? extends U> function);

  /**
   * Replace the item in this functor with a new item.
   *
   * @param item New item
   * @param <U>  Type of the new item
   * @return Functor with the new item inserted.
   */
  <U> Functor<U> replace(U item);
}
