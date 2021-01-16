package net.nergi.solutions;

import static net.nergi.Utils.arrayListOf;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This class contains testing for all solution classes.
 */
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

    assertEquals("ohway", Pf7c3.pigLatinise("oh"));
    assertEquals("Ohway", Pf7c3.pigLatinise("Oh"));
  }

  @Test
  public void getWords67dd() {
    assertEquals(arrayListOf("a", "new", "day"), P67dd.getWords("a new day"));
    assertEquals(arrayListOf("21", "12", "2012"), P67dd.getWords("21/12/2012"));
    assertEquals(
        arrayListOf("supercalifragilisticexpieleedotious"),
        P67dd.getWords("supercalifragilisticexpieleedotious")
    );
    assertEquals(arrayListOf("Area", "51"), P67dd.getWords("Area 51"));
  }

  @Test
  public void rectanglec2b8() {
    Pc2b8.Rectangle rectangle1 = new Pc2b8.Rectangle(
        new Pc2b8.Point(0, 0), 4, 4
    );

    Pc2b8.Rectangle rectangle2 = new Pc2b8.Rectangle(
        new Pc2b8.Point(1, 1), 2, 2
    );

    Pc2b8.Rectangle rectangle3 = new Pc2b8.Rectangle(
        new Pc2b8.Point(1, 0), 1, 1
    );

    assertEquals(16, rectangle1.area());
    assertEquals(4, rectangle2.area());
    assertEquals(1, rectangle3.area());

    assertEquals(
        new Pc2b8.Point(4, 4), rectangle1.getBottomRight()
    );
    assertEquals(
        new Pc2b8.Point(3, 3), rectangle2.getBottomRight()
    );
    assertEquals(
        new Pc2b8.Point(2, 1), rectangle3.getBottomRight()
    );

    assertTrue(rectangle1.contains(rectangle2));
    assertFalse(rectangle2.contains(rectangle1));

    assertTrue(rectangle1.contains(rectangle3));
    assertFalse(rectangle3.contains(rectangle1));

    assertFalse(rectangle2.contains(rectangle3));
    assertFalse(rectangle3.contains(rectangle2));
  }
}
