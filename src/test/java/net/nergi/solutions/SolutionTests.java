package net.nergi.solutions;

import static org.junit.jupiter.api.Assertions.*;

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

  @Test
  public void isPalindromicf79b() {
    assertTrue(Pf79b.isPalindromic("w"));
    assertTrue(Pf79b.isPalindromic("hh"));
    assertTrue(Pf79b.isPalindromic("wow"));

    assertFalse(Pf79b.isPalindromic("pantalones giganticus"));
    assertFalse(Pf79b.isPalindromic("oh no"));
    assertFalse(Pf79b.isPalindromic("not again"));
  }

  @Test
  public void pigLatinisef7c3() {
    assertEquals("eyhay", Pf7c3.pigLatinise("hey"));
    assertEquals("Eyhay", Pf7c3.pigLatinise("Hey"));

    assertEquals("oh", Pf7c3.pigLatinise("oh"));
    assertEquals("Oh", Pf7c3.pigLatinise("Oh"));
  }
}
