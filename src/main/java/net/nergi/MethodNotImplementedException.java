package net.nergi;

/**
 * Class that represents the fact that something has not been implemented inside a method. Extends
 * {@link UnsupportedOperationException} due to its similarity.
 */
@SuppressWarnings("unused")
public class MethodNotImplementedException extends UnsupportedOperationException {

  /** Creates a new <code>MethodNotImplementedException</code> */
  public MethodNotImplementedException() {
    super();
  }

  /** Creates a new <code>MethodNotImplementedException</code> with a given message. */
  public MethodNotImplementedException(String message) {
    super(message);
  }

  /** Creates a new <code>MethodNotImplementedException</code> with a message and a cause. */
  public MethodNotImplementedException(String message, Throwable cause) {
    super(message, cause);
  }
}
