package net.nergi.solutions;

import java.util.HashSet;
import java.util.Set;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P7206 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "7206: Understanding references";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    System.out.println(
        """
            PrintBook ends up causing a lot of shadowing due to a Set only containing unique items.
            Initially, the set contains { C++ (ISBN: 1) }, and the Haskell book is added perfectly fine as it does not contain a conflicting ISBN (which is the only thing used to define equality).
            The Java book will not be added to the set as toEquals treats it and the C++ book as being equal due to the equals function only being defined in terms of the ISBN.
            The main method still holds the reference to the C++ book, so it is able to modify it from the outside when calling setTitle.
            This results in the main finally printing the modified C++ book and the Haskell book.
            (See the source code at the question repository for more details.)
            """
    );

    // Execute PrintBook's main
    PrintBook.main(new String[0]);
  }

  public static class Book {

    private final int isbn;
    private String title;

    Book(int isbn, String title) {
      this.isbn = isbn;
      setTitle(title);
    }

    void setTitle(String title) {
      this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Book) {
        return isbn == ((Book) obj).isbn;
      } else {
        return false;
      }
    }

    @Override
    public int hashCode() {
      return isbn;
    }

    @Override
    public String toString() {
      return title + "(ISBN: " + isbn + ")";
    }

  }

  public static class PrintBook {

    public static void main(String[] args) {

      Set<Book> set = new HashSet<>();

      Book book = new Book(1, "C++");

      set.add(book);

      set.add(new Book(2, "Haskell"));
      set.add(new Book(1, "Java"));
      book.setTitle("New C++ book");

      for (Book b : set) {
        System.out.println(b);
      }

    }

  }
}
