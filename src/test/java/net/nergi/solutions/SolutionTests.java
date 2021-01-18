package net.nergi.solutions;

import static net.nergi.Utils.arrayListOf;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Supplier;
import net.nergi.solutions.Pe6fd.BitSet;
import net.nergi.solutions.Pe6fd.BitSet32;
import net.nergi.solutions.Pe6fd.BitSet64;
import net.nergi.solutions.Pe6fd.BitSet8;
import net.nergi.solutions.Pe6fd.BitSetArray;
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

  @Test
  public void bitSet36fd() {
    final Supplier<BitSet> intersectionTestSetSupplier = () -> {
      BitSet8 set = new BitSet8();
      set.add(1);
      set.add(2);

      return set;
    };

    // Initialise bit sets
    final BitSet8 bs8 = new BitSet8();
    final BitSet32 bs32 = new BitSet32();
    final BitSet64 bs64 = new BitSet64();
    final BitSetArray bsa = new BitSetArray(255);

    // BitSet8 tests
    bs8.add(1);
    assertTrue(bs8.contains(1));

    bs8.remove(1);
    assertFalse(bs8.contains(1));

    bs8.add(2);
    bs8.add(3);

    bs8.intersect(intersectionTestSetSupplier.get());
    assertTrue(bs8.contains(2));
    assertFalse(bs8.contains(3));

    assertThrows(IllegalArgumentException.class, () -> bs8.add(Integer.MAX_VALUE));
    assertThrows(IllegalArgumentException.class, () -> bs8.add(-1));

    // BitSet32 tests
    bs32.add(1);
    assertTrue(bs32.contains(1));

    bs32.remove(1);
    assertFalse(bs32.contains(1));

    bs32.add(2);
    bs32.add(3);

    bs32.intersect(intersectionTestSetSupplier.get());
    assertTrue(bs32.contains(2));
    assertFalse(bs32.contains(3));

    assertThrows(IllegalArgumentException.class, () -> bs32.add(Integer.MAX_VALUE));
    assertThrows(IllegalArgumentException.class, () -> bs32.add(-1));

    // BitSet64 tests
    bs64.add(1);
    assertTrue(bs64.contains(1));

    bs64.remove(1);
    assertFalse(bs64.contains(1));

    bs64.add(2);
    bs64.add(3);

    bs64.intersect(intersectionTestSetSupplier.get());
    assertTrue(bs64.contains(2));
    assertFalse(bs64.contains(3));

    assertThrows(IllegalArgumentException.class, () -> bs64.add(Integer.MAX_VALUE));
    assertThrows(IllegalArgumentException.class, () -> bs64.add(-1));

    // BitSetArray tests
    bsa.add(1);
    assertTrue(bsa.contains(1));

    bsa.remove(1);
    assertFalse(bsa.contains(1));

    bsa.add(2);
    bsa.add(3);

    bsa.intersect(intersectionTestSetSupplier.get());
    assertTrue(bsa.contains(2));
    assertFalse(bsa.contains(3));

    assertThrows(IllegalArgumentException.class, () -> bsa.add(Integer.MAX_VALUE));
    assertThrows(IllegalArgumentException.class, () -> bsa.add(-1));
  }

}
