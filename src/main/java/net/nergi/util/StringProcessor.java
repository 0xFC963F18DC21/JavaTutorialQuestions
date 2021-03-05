package net.nergi.util;

/**
 * Represents an object that can be used to process a string into another object.
 *
 * @param <T> Output type
 */
@FunctionalInterface
public interface StringProcessor<T> {

  /**
   * Process a string to the object output.
   *
   * @param str Input
   * @return    Object output obtained from the string.
   */
  T process(String str);
}
