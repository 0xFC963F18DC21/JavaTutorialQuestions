package net.nergi.solutions;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import net.nergi.Solution;

/** Solution for dc38. */
@SuppressWarnings("unused")
public class Pdc38 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "dc38: Email management system";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // Initialise group addresses
    final GroupAddress wholeCompany = new GroupAddress("pln404labs@nergi.net");
    final GroupAddress personnel = new GroupAddress("pln404labs-personnel@nergi.net");
    final GroupAddress higherups = new GroupAddress("pln404labs-higherups@nergi.net");

    // The whole company has everyone's emails in both subgroups.
    wholeCompany.addTarget(wholeCompany); // Potential oopsie
    wholeCompany.addTarget(personnel);
    wholeCompany.addTarget(higherups);

    // Personnel emails
    personnel.addTarget(new IndividualAddress("pln404labs-mzt@nergi.net"));
    personnel.addTarget(new IndividualAddress("pln404labs-mfa@nergi.net"));
    personnel.addTarget(new IndividualAddress("pln404labs-szn@nergi.net"));
    personnel.addTarget(new IndividualAddress("pln404labs-arc@nergi.net"));

    // Higherup emails
    higherups.addTarget(new IndividualAddress("pln404labs-azl@nergi.net"));
    higherups.addTarget(new IndividualAddress("pln404labs-nkz@nergi.net"));

    // Print all in all groups
    System.out.println("Everyone's emails:");
    wholeCompany.getTargets().forEach(System.out::println);
    System.out.print('\n');

    // Print personnel emails
    System.out.println("Personnel emails:");
    personnel.getTargets().forEach(System.out::println);
    System.out.print('\n');

    // Print higherup emails
    System.out.println("Higherups' emails:");
    higherups.getTargets().forEach(System.out::println);
  }

  public abstract static class EmailAddress {

    private static final WeakHashMap<String, WeakReference<String>> used = new WeakHashMap<>();
    protected final String identifier;

    private EmailAddress() {
      this("INVALID");
    }

    protected EmailAddress(String identifier) {
      if (used.get(identifier) != null) {
        throw new IllegalArgumentException("Email address has already been used!");
      }

      this.identifier = identifier;
      used.put(identifier, new WeakReference<>(identifier));
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof EmailAddress) {
        return identifier.equals(((EmailAddress) o).identifier);
      } else {
        return false;
      }
    }

    @Override
    public int hashCode() {
      return identifier.hashCode();
    }

    @Override
    public String toString() {
      return identifier;
    }

    public abstract Set<IndividualAddress> getTargets();
  }

  public static class IndividualAddress extends EmailAddress {

    public IndividualAddress(String identifier) {
      super(identifier);
    }

    @Override
    public Set<IndividualAddress> getTargets() {
      return Set.of(this);
    }
  }

  public static class GroupAddress extends EmailAddress {

    private final Set<EmailAddress> targets;

    public GroupAddress(String identifier) {
      super(identifier);
      targets = new HashSet<>();
    }

    public void addTarget(EmailAddress target, boolean shouldThrow)
        throws IllegalArgumentException {
      if (shouldThrow) {
        final ArrayDeque<EmailAddress> toVisit = new ArrayDeque<>();
        toVisit.add(target);

        while (!toVisit.isEmpty()) {
          final EmailAddress current = toVisit.pop();

          if (current == this) {
            throw new IllegalArgumentException("Cyclic addition!");
          }

          if (current instanceof GroupAddress) {
            toVisit.addAll(((GroupAddress) current).targets);
          }
        }
      }

      targets.add(target);
    }

    public void addTarget(EmailAddress target) {
      addTarget(target, false);
    }

    @Override
    public Set<IndividualAddress> getTargets() throws IllegalArgumentException {
      // BFS with visit collection
      final HashMap<EmailAddress, Boolean> visited = new HashMap<>();
      visited.put(this, false);

      // Resultant set
      final Set<IndividualAddress> result = new HashSet<>();

      // Addresses to visit
      final ArrayDeque<EmailAddress> toVisit =
          targets.stream()
              .filter((e) -> visited.get(e) == null)
              .collect(Collectors.toCollection(ArrayDeque::new));

      // Run BFS
      while (!toVisit.isEmpty()) {
        final EmailAddress currentAddress = toVisit.pop();
        visited.put(currentAddress, true);

        if (currentAddress instanceof IndividualAddress) {
          result.add((IndividualAddress) currentAddress);
        } else if (currentAddress instanceof GroupAddress) {
          final GroupAddress ga = (GroupAddress) currentAddress;

          toVisit.addAll(
              ga.targets.stream()
                  .filter((e) -> visited.get(e) == null)
                  .collect(Collectors.toSet()));
        } else {
          throw new IllegalArgumentException("Where did this invalid type come from?");
        }
      }

      return result;
    }
  }
}
