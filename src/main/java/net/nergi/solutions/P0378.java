package net.nergi.solutions;

import net.nergi.Solution;

@SuppressWarnings("unused")
public class P0378 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "0378: Comparing people";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    // Demo class is substituted with this method.
  }

  public interface PersonComparator {
    int compareTo(Person a, Person b);
  }

  // Strings already implement Comparable which compares by lexicographic order.
  public static class SurnameComparator implements PersonComparator {
    @Override
    public int compareTo(Person a, Person b) {
      return a.getSurname().compareTo(b.getSurname());
    }
  }

  public static class ForenamesComparator implements PersonComparator {
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
}
