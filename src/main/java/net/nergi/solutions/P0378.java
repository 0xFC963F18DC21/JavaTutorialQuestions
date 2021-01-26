package net.nergi.solutions;

import java.util.Set;
import java.util.stream.Collectors;
import net.nergi.Solution;

/** Solution for 0378. */
@SuppressWarnings("unused")
public class P0378 implements Solution {

  public static Person findMin(Set<Person> people, PersonComparator comparator) {
    Person min = null;
    for (Person p : people) {
      if (min == null) {
        min = p;
      } else {
        if (comparator.compareTo(p, min) == -1) {
          min = p;
        }
      }
    }

    return min;
  }

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "0378: Comparing people";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // Demo class is substituted with this method.
    Person alan = new Person("Alan", "Dylan", "+4475-2123-1233");
    Person bob = new Person("Bob", "Dylan", "+4475-2312-9841");
    Person charles = new Person("Charles", "Calvin", "+1771-2345-5123");

    Set<Person> people = Set.of(alan, bob, charles);

    // Make comparator instances
    PersonComparator surComp = new SurnameComparator();
    PersonComparator forComp = new ForenameComparator();
    PersonComparator telComp = new TelephoneNumberComparator();

    TwoTieredComparator surForComp = new TwoTieredComparator(surComp, forComp);

    ThreeTieredComparator surForTelComp = new ThreeTieredComparator(surComp, forComp, telComp);

    // Print all people
    for (Person p : people) {
      System.out.println(p);
    }

    // Test
    System.out.println("Alan & Bob: " + forComp.compareTo(alan, bob));
    System.out.println("Dylan & Calvin: " + surComp.compareTo(bob, charles));
    System.out.println("Alan & Bob's Telephone Number: " + telComp.compareTo(alan, bob));

    System.out.println(people.stream().sorted(surComp::compareTo).collect(Collectors.toList()));
    System.out.println(people.stream().sorted(surForComp::compareTo).collect(Collectors.toList()));
    System.out.println(
        people.stream().sorted(surForTelComp::compareTo).collect(Collectors.toList()));
  }

  public interface PersonComparator {

    int compareTo(Person a, Person b);
  }

  public static class Person {

    private String forename;
    private String surname;
    private String telephone;

    public Person(String forename, String surname, String telephone) {
      this.forename = forename;
      this.surname = surname;
      this.telephone = telephone;
    }

    public String getForename() {
      return forename;
    }

    public void setForename(String forename) {
      this.forename = forename;
    }

    public String getSurname() {
      return surname;
    }

    public void setSurname(String surname) {
      this.surname = surname;
    }

    public String getTelephone() {
      return telephone;
    }

    public void setTelephone(String telephone) {
      this.telephone = telephone;
    }

    @Override
    public String toString() {
      return surname + ", " + forename + " (Tel: " + telephone + ")";
    }
  }

  // Strings already implement Comparable which compares by lexicographic order.
  public static class SurnameComparator implements PersonComparator {

    @Override
    public int compareTo(Person a, Person b) {
      return a.getSurname().compareTo(b.getSurname());
    }
  }

  public static class ForenameComparator implements PersonComparator {

    @Override
    public int compareTo(Person a, Person b) {
      return a.getForename().compareTo(b.getForename());
    }
  }

  public static class TelephoneNumberComparator implements PersonComparator {

    @Override
    public int compareTo(Person a, Person b) {
      return a.getTelephone().compareTo(b.getTelephone());
    }
  }

  public static class TwoTieredComparator implements PersonComparator {

    private final PersonComparator first;
    private final PersonComparator second;

    public TwoTieredComparator(PersonComparator first, PersonComparator second) {
      this.first = first;
      this.second = second;
    }

    @Override
    public int compareTo(Person a, Person b) {
      int firstReturn = first.compareTo(a, b);

      if (firstReturn != 0) {
        return firstReturn;
      } else {
        return second.compareTo(a, b);
      }
    }
  }

  public static class ThreeTieredComparator implements PersonComparator {

    private final TwoTieredComparator cmp;

    public ThreeTieredComparator(
        PersonComparator first, PersonComparator second, PersonComparator third) {
      cmp = new TwoTieredComparator(new TwoTieredComparator(first, second), third);
    }

    @Override
    public int compareTo(Person a, Person b) {
      return cmp.compareTo(a, b);
    }
  }
}
