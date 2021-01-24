package net.nergi.solutions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P9a9b implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "9a9b: Transposing tunes";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    Tune frereJaques = new PhysicalTune();
    Tune frereJaquesTransposedUp = frereJaques.transpose(3);
    Tune frereJaquesTransposedUpAnotherOctave = frereJaquesTransposedUp.transpose(12);

    frereJaques.addTuneElement(new Note(NoteName.C, 4, NoteValue.QUARTER));
    frereJaques.addTuneElement(new Note(NoteName.D, 4, NoteValue.QUARTER));
    frereJaques.addTuneElement(new Note(NoteName.C, 4, NoteValue.QUARTER));
    frereJaques.addTuneElement(new Rest(NoteValue.QUARTER));

    System.out.println("Frere Jaques:\n  " + frereJaques);
    System.out.println("Frere Jaques up three semitones:\n" + frereJaquesTransposedUp);
    System.out.println(
        "Frere Jaques up another octave:\n" + frereJaquesTransposedUpAnotherOctave
    );
  }

  public interface Tune extends Iterable<TuneElement> {

    // Add an element to the tune
    void addTuneElement(TuneElement tuneElement);

    // Return a tune derived from the original, transposed up
    // three semitones
    Tune transpose(int interval);

  }

  public abstract static class AbstractTune implements Tune {

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder();

      for (TuneElement te : this) {
        sb.append(te.toString()).append(" ");
      }

      if (sb.length() > 0) {
        sb.setLength(sb.length() - 1);
      }

      return sb.toString();
    }

    @Override
    public Tune transpose(int interval) {
      return new TransposedTune(this, interval);
    }

  }

  public abstract static class TuneElement {

    protected final NoteValue value;

    private TuneElement() {
      this.value = NoteValue.QUARTER;
    }

    protected TuneElement(NoteValue value) {
      this.value = value;
    }

    public NoteValue getValue() {
      return value;
    }

  }

  public enum NoteName {

    C, C_SHARP, D, D_SHARP, E, F, F_SHARP, G, G_SHARP, A, A_SHARP, B;

    @Override
    public String toString() {
      return switch (this) {
        case C -> "C";
        case C_SHARP -> "C#";
        case D -> "D";
        case D_SHARP -> "D#";
        case E -> "E";
        case F -> "F";
        case F_SHARP -> "F#";
        case G -> "G";
        case G_SHARP -> "G#";
        case A -> "A";
        case A_SHARP -> "A#";
        case B -> "B";
      };
    }

  }

  public enum NoteValue {

    WHOLE, HALF, QUARTER, EIGHTH, SIXTEENTH;

    @Override
    public String toString() {
      return switch (this) {
        case WHOLE -> "1/1";
        case HALF -> "1/2";
        case QUARTER -> "1/4";
        case EIGHTH -> "1/8";
        case SIXTEENTH -> "1/16";
      };
    }

  }

  public static class PhysicalTune extends AbstractTune {

    private final List<TuneElement> elements = new ArrayList<>();

    @Override
    public void addTuneElement(TuneElement tuneElement) {
      elements.add(tuneElement);
    }

    @Override
    public Iterator<TuneElement> iterator() {
      return elements.iterator();
    }

  }

  public static class TransposedTune extends AbstractTune {

    private final int interval;
    private final Tune original;

    public TransposedTune(Tune original, int interval) {
      this.interval = interval;
      this.original = original;
    }

    @Override
    public void addTuneElement(TuneElement tuneElement) {
      if (tuneElement instanceof Note) {
        original.addTuneElement(((Note) tuneElement).transpose(-interval));
      } else {
        original.addTuneElement(tuneElement);
      }
    }

    @Override
    public Iterator<TuneElement> iterator() {
      return new Iterator<>() {

        private final Iterator<TuneElement> backingIterator = original.iterator();

        @Override
        public boolean hasNext() {
          return backingIterator.hasNext();
        }

        @Override
        public TuneElement next() {
          final TuneElement te = backingIterator.next();

          if (te instanceof Note) {
            return ((Note) te).transpose(interval);
          } else {
            return te;
          }
        }

      };
    }

  }

  public static class Note extends TuneElement {

    private final NoteName name;
    private final int octave;

    public Note(NoteName name, int octave, NoteValue value) {
      super(value);
      this.name = name;
      this.octave = octave;
    }

    public NoteName getName() {
      return name;
    }

    public int getOctave() {
      return octave;
    }

    public Note transpose(int interval) {
      // Needs scale length
      final int scaleSize = NoteName.values().length;
      final int notePlace = scaleSize * octave + name.ordinal();
      final int transposed = notePlace + interval;

      return new Note(
          NoteName.values()[transposed % scaleSize],
          transposed / scaleSize,
          value
      );
    }

    @Override
    public String toString() {
      return name + "" + octave + "(" + value + ")";
    }

  }

  public static class Rest extends TuneElement {

    public Rest(NoteValue value) {
      super(value);
    }

    @Override
    public String toString() {
      return "Rest(" + value + ")";
    }

  }

}
