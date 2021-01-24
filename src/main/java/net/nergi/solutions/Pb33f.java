package net.nergi.solutions;

import static net.nergi.Utils.pass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.nergi.Solution;
import net.nergi.solutions.P888a.ImmutablePair;

@SuppressWarnings("unused")
public class Pb33f implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "b33f: Logging using a functional interface";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    // Avoiding testing the file classes for now...
    final Logger consoleLogger = (level, message) -> System.out.println(level + ": " + message);
    final StringInspector inspector = new KnownWordsStringInspector(
        new HashSet<>(Set.of("null", "error")),
        new HashSet<>(Set.of("log", "string"))
    );

    // Run inspectors
    final var testInspect1 = inspector.inspect("Be careful of null pointers!");
    final var testInspect2 = inspector.inspect("Log this for later...");
    final var testInspect3 = inspector.inspect("I am plain.");
    final var testInspect4 = inspector.inspect(
        "Log your error and exception instances so you can see where they are."
    );

    // Print results
    testInspect1.ifPresent((pair) -> consoleLogger.log(pair.getFirst(), pair.getSecond()));
    testInspect2.ifPresent((pair) -> consoleLogger.log(pair.getFirst(), pair.getSecond()));
    testInspect3.ifPresent((pair) -> consoleLogger.log(pair.getFirst(), pair.getSecond()));
    testInspect4.ifPresent((pair) -> consoleLogger.log(pair.getFirst(), pair.getSecond()));
  }

  public enum LogLevel {
    VERBOSE, INFO, WARNING, ERROR, FATAL
  }

  @FunctionalInterface
  public interface Logger {

    void log(LogLevel logLevel, String message);

  }

  @FunctionalInterface
  public interface StringInspector {

    Optional<ImmutablePair<LogLevel, String>> inspect(String string);

  }

  public static class FileInspector {

    private final Logger internalLogger;

    public FileInspector(Logger internalLogger) {
      this.internalLogger = internalLogger;
    }

    public void inspectFile(String filePath, StringInspector inspector) {
      try {
        final BufferedReader fileReader = new BufferedReader(
            new FileReader(filePath)
        );

        String line;
        do {
          line = fileReader.readLine();
          inspector.inspect(line).ifPresent(
              (pair) -> internalLogger.log(pair.getFirst(), pair.getSecond())
          );
        } while (line != null);
      } catch (FileNotFoundException e) {
        internalLogger.log(LogLevel.FATAL, "File not found: " + filePath);
      } catch (IOException e) {
        internalLogger.log(LogLevel.FATAL, "IO Exception: " + e.toString());
      }
    }

  }

  public static class FileLogger implements Logger {

    private final BufferedWriter outputWriter;

    public FileLogger(BufferedWriter outputWriter) {
      this.outputWriter = outputWriter;
    }

    public FileLogger(String filePath) throws IOException {
      this.outputWriter = new BufferedWriter(
          new FileWriter(filePath)
      );
    }

    @Override
    public void log(LogLevel logLevel, String message) throws RuntimeException {
      try {
        switch (logLevel) {
          case INFO, VERBOSE -> pass();
          default -> outputWriter.write(logLevel + ": " + message);
        }
      } catch (IOException e) {
        throw new RuntimeException("FileLogger's BufferedWriter failed to output to file!", e);
      }
    }

  }

  public static class KnownWordsStringInspector implements StringInspector {

    private final Set<String> errorWords;
    private final Set<String> verboseWords;

    public KnownWordsStringInspector(
        Set<String> errorWords, Set<String> verboseWords
    ) {
      this.errorWords = errorWords.stream()
          .map(String::toLowerCase)
          .collect(Collectors.toSet());

      this.verboseWords = verboseWords.stream()
          .map(String::toLowerCase)
          .collect(Collectors.toSet());
    }

    @Override
    public Optional<ImmutablePair<LogLevel, String>> inspect(String string) {
      final Set<String> observedErrorWords = new HashSet<>();
      final Set<String> observedVerboseWords = new HashSet<>();

      for (String s : string.split(" ")) {
        if (errorWords.contains(s.toLowerCase())) {
          observedErrorWords.add(s);
        }

        if (verboseWords.contains(s.toLowerCase())) {
          observedVerboseWords.add(s);
        }
      }

      final StringBuilder sb = new StringBuilder();
      sb.append("The observed set of words are...\n")
          .append("  Error Words: ").append(observedErrorWords).append("\n")
          .append("Verbose Words: ").append(observedVerboseWords);

      if (observedErrorWords.isEmpty() && observedVerboseWords.isEmpty()) {
        return Optional.empty();
      } else if (observedErrorWords.isEmpty()) {
        return Optional.of(new ImmutablePair<>(LogLevel.VERBOSE, sb.toString()));
      } else {
        return Optional.of(new ImmutablePair<>(LogLevel.ERROR, sb.toString()));
      }
    }
  }

}
