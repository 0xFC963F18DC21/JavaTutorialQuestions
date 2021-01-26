package net.nergi;

@SuppressWarnings("unused")
public class MethodNotImplementedException extends Exception {

  public MethodNotImplementedException() {
    super();
  }

  public MethodNotImplementedException(String message) {
    super(message);
  }

  public MethodNotImplementedException(String message, Throwable cause) {
    super(message, cause);
  }
}
