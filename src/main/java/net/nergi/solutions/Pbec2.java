package net.nergi.solutions;

import java.util.ArrayList;
import java.util.stream.Collectors;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class Pbec2 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "bec2: Music collection";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    Artist neilE = new Artist("Neil Everton", Genre.JAZZ);
    neilE.addRecord("Small Staircase");
    neilE.addRecord("Tuplets for You and Me");
    neilE.addRecord("Fire under the North Star");
    neilE.addRecord("Under the Fallen Bridge", Genre.ROCK);
    neilE.addRecord("A New Life", Genre.POP);

    System.out.println(neilE);
  }

  private enum Genre {
    ROCK, POP, JAZZ;


    @Override
    public String toString() {
      return switch (this) {
        case ROCK -> "Rock";
        case POP -> "Pop";
        case JAZZ -> "Jazz";
      };
    }
  }

  private static class Record {

    public final String title;

    public final Genre genre;

    public Record(String title, Genre genre) {
      this.title = title;
      this.genre = genre;
    }

    @Override
    public String toString() {
      return title + " (" + genre + ")";
    }
  }

  private static class Artist {

    public final String name;

    private final ArrayList<Record> catalogue = new ArrayList<>();

    public final Genre mainGenre;

    public Artist(String name, Genre mainGenre) {
      this.name = name;
      this.mainGenre = mainGenre;
    }

    public void addRecord(String name) {
      catalogue.add(new Record(name, mainGenre));
    }

    public void addRecord(String name, Genre genre) {
      catalogue.add(new Record(name, genre));
    }

    public void showCatalogue() {
      System.out.println(catalogue);
    }

    public void showGenre(Genre genre) {
      System.out.println(
          catalogue.stream().filter((rec) -> rec.genre == genre).collect(Collectors.toList())
      );
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder();

      sb.append(name)
          .append("\nMain genre: ")
          .append(mainGenre.toString())
          .append("\nCatalogue: \n");

      for (Record r : catalogue) {
        sb.append("- ").append(r.toString()).append("\n");
      }

      return sb.toString();
    }
  }
}
