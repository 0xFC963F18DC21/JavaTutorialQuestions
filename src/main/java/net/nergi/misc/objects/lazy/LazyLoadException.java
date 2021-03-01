package net.nergi.misc.objects.lazy;

/** Signifies that an exception has occurred while lazy-loading a value. */
public class LazyLoadException extends RuntimeException {

  public LazyLoadException(String message) {
    super(message);
  }

  public LazyLoadException(String message, Throwable cause) {
    super(message, cause);
  }

  public LazyLoadException(Throwable cause) {
    super(cause);
  }
}
