package net.nergi.solutions;

import static net.nergi.Utils.mutableListOf;

import java.util.List;
import java.util.Random;
import net.nergi.Solution;

/** Solution for 8f65. */
@SuppressWarnings("unused")
public class P8f65 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "8f65: Lucky battling fighters with inheritance";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    // Oops! I've already done this in the other class before reading this question.
    // Enjoy this battle on me:
    final Random usedRandom = new Random();

    final List<String> possibleNames = mutableListOf("Alice", "Bob", "Charlie");
    final List<String> possibleTypes = mutableListOf("Barbarian", "Mage", "Archer");

    final P7ec8.Fighter fighter1 =
        new P7ec8.Fighter(
            possibleNames.remove(usedRandom.nextInt(possibleNames.size())),
            possibleTypes.get(usedRandom.nextInt(possibleTypes.size())),
            usedRandom);

    final P8d24.LuckyFighter fighter2 =
        new P8d24.LuckyFighter(
            possibleNames.remove(usedRandom.nextInt(possibleNames.size())),
            possibleTypes.get(usedRandom.nextInt(possibleTypes.size())),
            usedRandom);

    P7ec8.GameEngine.simulateBattle(fighter1, fighter2);
  }
}
