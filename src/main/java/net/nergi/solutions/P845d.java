package net.nergi.solutions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.nergi.Solution;

@SuppressWarnings("unused")
public class P845d implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "845d: Books and dictionaries";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    final Bookshelf shelf = new Bookshelf();
    shelf.addBookOnRightSide(new Book("231-2-34-158689-1", "How to suffer in Java", 1200));
    shelf.addBookOnLeftSide(new Book("555-3-78-346277-6", "How to suffer in C++", 600));
    shelf.addBookOnRightSide(new Book("612-9-79-234689-6", "How to suffer in Fortran", 100));
    shelf.addBookOnLeftSide(
        new Dictionary("012-3-45-6789101-1", "Conlang Guide", 8800, "English", "Many", 240000));
    shelf.addBookOnRightSide(
        new Dictionary(
            "462-5-12-286792-0",
            "Standard English Dictionary",
            10000,
            "English",
            "English",
            25000));
    shelf.addBookOnLeftSide(new Book("678-3-88-968927-9", "How to suffer in PHP", 500));

    // First print
    shelf.printLeftToRight();

    System.out.println("-------------------------------------------------------------");

    // Sort
    sortBookshelf(shelf);
    shelf.printLeftToRight();

    System.out.println("-------------------------------------------------------------");
  }

  public static class Book {

    private final String isbn;
    private final String title;
    private final int pages;

    public Book(String isbn, String title, int pages) {
      this.isbn = isbn;
      this.title = title;
      this.pages = pages;
    }

    @Override
    public String toString() {
      return title + ", ISBN: " + isbn + ", pages: " + pages;
    }
  }

  public static class Dictionary extends Book {

    private final String sourceLanguage;
    private final String targetLanguage;
    private final int numDefinitions;

    public Dictionary(
        String isbn,
        String title,
        int pages,
        String sourceLanguage,
        String targetLanguage,
        int numDefinitions) {
      super(isbn, title, pages);
      this.sourceLanguage = sourceLanguage;
      this.targetLanguage = targetLanguage;
      this.numDefinitions = numDefinitions;
    }

    @Override
    public String toString() {
      return String.format(
          "%s, type: %s-%s, definitions: %d",
          super.toString(), sourceLanguage, targetLanguage, numDefinitions);
    }
  }

  public static class Bookshelf {

    private final List<Book> books = new LinkedList<>();

    // Returns the number of books on the bookshelf
    public int size() {
      return books.size();
    }

    // Adds a book on the left side of the shelf.
    // Shifts all other books one position to the right
    public void addBookOnLeftSide(Book b) {
      books.add(0, b);
    }

    // Adds a book on the right side of the shelf.
    public void addBookOnRightSide(Book b) {
      books.add(b);
    }

    // Adds book in position i counting from the left, starting
    // from zero, and shifts all other books one position to the right.
    // Hence addBook(0, b) is equivalent to addBookOnLeftSide(b), and
    // addBook(size(), b) is equivalent to addBookOnRightSide(b)
    public void addBook(int i, Book b) {
      books.add(i, b);
    }

    // Removes book from position i, shifting all books with position
    // greater than i to the left
    public Book remove(int i) {
      return books.remove(i);
    }

    // Prints all details of books from left to right
    public void printLeftToRight() {
      books.forEach(System.out::println);
    }

    // Prints all details of books from right to left
    public void printRightToLeft() {
      for (int i = books.size() - 1; i >= 0; --i) {
        System.out.println(books.get(i));
      }
    }
  }

  public static void sortBookshelf(Bookshelf shelf) {
    final ArrayList<Book> books = new ArrayList<>();
    final ArrayList<Book> dicts = new ArrayList<>();

    while (shelf.size() > 0) {
      final Book b = shelf.remove(0);

      if (b instanceof Dictionary) {
        dicts.add(b);
      } else {
        books.add(b);
      }
    }

    dicts.forEach(shelf::addBookOnLeftSide);
    books.forEach(shelf::addBookOnLeftSide);
  }
}
