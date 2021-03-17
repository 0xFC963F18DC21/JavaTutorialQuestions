package net.nergi.util;

/** Signifies that an exception failed to be suppressed. */
public class FallThroughException extends RuntimeException {

  public FallThroughException() {}

  public FallThroughException(String message) {
    super(message);
  }

  public FallThroughException(String message, Throwable cause) {
    super(message, cause);
  }

  public FallThroughException(Throwable cause) {
    super(cause);
  }
}
