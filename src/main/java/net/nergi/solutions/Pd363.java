package net.nergi.solutions;

import net.nergi.Solution;

@SuppressWarnings("unused")
public class Pd363 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "d363: Bloated person";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    System.out.println("There is no execution to be done here. Please see the source code.");
  }

  /* ---------------------------------------------------------
   * See Pd363-Refactorings.txt for more details on the fixes.
   * ---------------------------------------------------------
   */

  public static class Date {

    public final int day;
    public final int month;
    public final int year; // In this refactoring, year is the full year, not the last 2 digits.

    public Date(int day, int month, int year) {
      this.day = day;
      this.month = month;
      this.year = year;
    }

    public boolean isDateValid() {
      if (day < 1) {
        return false;
      }

      // I'm not sure how to improve this...
      switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
          if (day > 31) {
            return false;
          }
          break;
        case 4:
        case 6:
        case 9:
        case 11:
          if (day > 30) {
            return false;
          }
          break;
        default:
          assert month == 2;
          if (day > (isLeapYear() ? 29 : 28)) {
            return false;
          }
      }

      return true;

    }

    private boolean isLeapYear() {
      // Deliberately simplified version of
      // leap year calculation
      return (year % 4) == 0;
    }

  }

  public static class Address {

    public final int houseNumber;
    public final String address1;
    public final String address2;
    public final String postCode;

    public Address(int houseNumber, String address1, String address2, String postCode) {
      this.houseNumber = houseNumber;
      this.address1 = address1;
      this.address2 = address2;
      this.postCode = postCode;
    }

    @Override
    public int hashCode() {
      return houseNumber + (address1.hashCode() * 31) + (address2.hashCode() * 31 * 31) + (
          postCode.hashCode() * 31 * 31 * 31);
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Address)) {
        return false;
      }

      Address other = (Address) obj;
      return houseNumber == other.houseNumber
          && address1.equals(other.address1)
          && address2.equals(other.address2)
          && postCode.equals(other.postCode);
    }
  }

  public static class Person {

    private final String forenames;
    private final String surname;

    private final Date dateOfBirth;

    private final Address address;

    private final String nationalInsuranceNumber;

    public Person(String forenames, String surname,
        Date dateOfBirth,
        Address address,
        String nationalInsuranceNumber) {
      this.forenames = forenames;
      this.surname = surname;
      this.dateOfBirth = dateOfBirth;
      this.address = address;
      this.nationalInsuranceNumber = nationalInsuranceNumber;
    }

    public boolean isDateOfBirthValid() {
      return dateOfBirth.isDateValid()
          && dateOfBirth.year >= 2000
          && dateOfBirth.year <= 2012;
    }

    public boolean sameAddress(Person other) {
      return address.equals(other.address);
    }

    public String getInitials() {
      final StringBuilder result = new StringBuilder();
      for (String fname : forenames.split(" ")) {
        result.append(fname.charAt(0));
      }

      return result.toString() + surname.charAt(0);
    }

    public String toString() {
      return "Name: " + forenames + " " + surname + "\n"
          + "DOB: " + dateOfBirth.day + "/" + dateOfBirth.month + "/" + dateOfBirth.year + "\n"
          + "Address: " + address.houseNumber + " " + address.address1 + ", " + address.address2
          + ", " + address.postCode + "\n"
          + "NI: " + nationalInsuranceNumber;
    }

  }
}
