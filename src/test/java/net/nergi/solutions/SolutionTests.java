package net.nergi.solutions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** This class contains testing for all solution classes. */
public class SolutionTests {

  @Test
  public void next98e3() {
    assertEquals(16, P98e3.next(5));
    assertEquals(8, P98e3.next(16));
    assertEquals(10, P98e3.next(3));
    assertEquals(3, P98e3.next(6));
  }
}
