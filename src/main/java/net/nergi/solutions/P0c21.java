package net.nergi.solutions;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P0c21 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "0c21: Properties";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    final PropertyCollection propCollection = new PropertyCollection();

    for (int i = 0; i < 1000; ++i) {
      propCollection.addProperty(new SemiDetachedHouse());
      propCollection.addProperty(new TeracedHouse());
    }

    for (int i = 0; i < 100; ++i) {
      propCollection.addProperty(new DetachedHouse());
      propCollection.addProperty(new SemiDetachedBungalow());
      propCollection.addProperty(new Flat());
    }

    for (int i = 0; i < 20; ++i) {
      propCollection.addProperty(new Maisonette());
      propCollection.addProperty(new DetachedBungalow());
      propCollection.addProperty(new TeracedBungalow());
    }

    System.out.println("         Houses: " + propCollection.getHouses().size());
    System.out.println("      Bungalows: " + propCollection.getBungalows().size());
    System.out.println("          Flats: " + propCollection.getFlats().size());
    System.out.println("    Maisonettes: " + propCollection.getMaisonettes().size());
    System.out.println("Terraced Houses: " + propCollection.getTerracedHouses().size());
  }

  public interface Property {}

  public static class PropertyCollection {

    final ArrayList<Property> properties = new ArrayList<>();

    // Add a property to the collection
    public void addProperty(Property p) {
      properties.add(p);
    }

    // Return the set of all houses in the collection
    public Set<House> getHouses() {
      return properties.stream()
          .filter((p) -> p instanceof House)
          .map((p) -> (House) p)
          .collect(Collectors.toSet());
    }

    // Return the set of all bungalows in the collection
    public Set<Bungalow> getBungalows() {
      return properties.stream()
          .filter((p) -> p instanceof Bungalow)
          .map((p) -> (Bungalow) p)
          .collect(Collectors.toSet());
    }

    // Return the set of all flats in the collection
    public Set<Flat> getFlats() {
      return properties.stream()
          .filter((p) -> p instanceof Flat)
          .map((p) -> (Flat) p)
          .collect(Collectors.toSet());    }

    // Return the set of all maisonettes in the collection
    public Set<Maisonette> getMaisonettes() {
      return properties.stream()
          .filter((p) -> p instanceof Maisonette)
          .map((p) -> (Maisonette) p)
          .collect(Collectors.toSet());
    }

    // Return the set of all terraced houses in the collection
    public Set<TeracedHouse> getTerracedHouses() {
      return properties.stream()
          .filter((p) -> p instanceof TeracedHouse)
          .map((p) -> (TeracedHouse) p)
          .collect(Collectors.toSet());
    }

  }

  public interface House extends Property {}

  public static class TeracedHouse implements House {}

  public static class SemiDetachedHouse implements House {}

  public static class DetachedHouse implements House {}

  public interface Bungalow extends House {}

  public static class TeracedBungalow extends TeracedHouse implements Bungalow {}

  public static class SemiDetachedBungalow extends SemiDetachedHouse implements Bungalow {}

  public static class DetachedBungalow extends DetachedHouse implements Bungalow {}

  public static class Flat implements Property {}

  public static class Maisonette extends Flat {}

}
