package net.nergi.solutions;

import static net.nergi.Utils.arrayListOf;

import java.util.ArrayList;
import java.util.Random;
import net.nergi.Solution;

/** Solution for 7ec8. */
@SuppressWarnings("unused")
public class P7ec8 implements Solution {

  /** Returns the header for the solution, which is the problem's name. */
  @Override
  public String getName() {
    return "7ec8: Battling fighters";
  }

  /** Runs the solution to the problem. */
  @Override
  public void exec() {
    final Random usedRandom = new Random();

    ArrayList<String> possibleNames = arrayListOf("Alice", "Bob", "Charlie");
    ArrayList<String> possibleTypes = arrayListOf("Barbarian", "Mage", "Archer");

    final Fighter fighter1 =
        new Fighter(
            possibleNames.remove(usedRandom.nextInt(possibleNames.size())),
            possibleTypes.get(usedRandom.nextInt(possibleTypes.size())),
            usedRandom);

    final Fighter fighter2 =
        new Fighter(
            possibleNames.remove(usedRandom.nextInt(possibleNames.size())),
            possibleTypes.get(usedRandom.nextInt(possibleTypes.size())),
            usedRandom);

    GameEngine.simulateBattle(fighter1, fighter2);
  }

  /**
   * A class that simulates an RPG-style character. The character has various statistics and
   * provides methods for battle interactions with other characters.
   */
  public static class Fighter {

    /** Maximum starting skill. */
    protected static final int SKILL_UPPER_BOUND = 18;

    /** Maximum starting stamina. */
    protected static final int STAMINA_UPPER_BOUND = 24;

    /** Fighter's name. */
    protected String name;

    /** Fighter's type / RPG class. */
    protected String type;

    /** Fighter's (fixed) skill level. */
    protected int skill;

    /** {@link Random} instance used for deciding battle rolls. */
    protected Random actionRnd;

    /** Fighter's current stamina. */
    protected int stamina;

    /** Creates a default-value fighter with no unique name or type. */
    public Fighter() {
      this("Unnamed", "Untyped", 1, 1, new Random());
    }

    /**
     * Creates a fighter with the given parameters.
     *
     * @param name Name of fighter
     * @param type Type of fighter
     * @param skill Skill level of fighter
     * @param stamina Starting stamila of fighter
     * @param rnd {@link Random} instance used for deciding battle rolls
     * @throws IllegalArgumentException Throws if skill or stamina out of range.
     */
    public Fighter(String name, String type, int skill, int stamina, Random rnd)
        throws IllegalArgumentException {
      this.name = name;
      this.type = type;

      if (skill < 1 || skill > SKILL_UPPER_BOUND) {
        throw new IllegalArgumentException(
            "Skill initialiser out of bounds. Must be 1 <= skill <= " + SKILL_UPPER_BOUND + ".");
      } else {
        this.skill = skill;
      }

      if (stamina < 1 || stamina > STAMINA_UPPER_BOUND) {
        throw new IllegalArgumentException(
            "Stamina initialiser out of bounds. Must be 1 <= stamina <= "
                + STAMINA_UPPER_BOUND
                + ".");
      } else {
        this.stamina = stamina;
      }

      this.actionRnd = rnd;
    }

    /**
     * Create a fighter with a given name and type, but with random skill and stamina values.
     *
     * @param name Name of fighter
     * @param type Type of fighter
     * @param generator {@link Random} instance used to generate stats and further used for battle
     *     rolls.
     */
    public Fighter(String name, String type, Random generator) {
      this(
          name,
          type,
          generator.nextInt(SKILL_UPPER_BOUND) + 1,
          generator.nextInt(STAMINA_UPPER_BOUND) + 1,
          generator);
    }

    /**
     * Reduce the fighter's stamina by the amount of damage taken. The value will never go below
     * zero.
     *
     * @param damage Damage taken
     */
    public void takeDamage(int damage) {
      stamina = Math.max(0, stamina - damage);
    }

    /** Calculates the max amount of damage to send to the opponent. */
    public int calculateDamage() {
      return 2; // I don't see the point of this?
    }

    /** Calculates the attack score for the fighter's current turn. */
    public int calculateAttackScore() {
      // Assuming 2d6
      return skill + (actionRnd.nextInt(6) + 1) + (actionRnd.nextInt(6) + 1);
    }

    /** Returns true iff the current fighter's stamina is zero. */
    //              Heavy
    public boolean isDead() {
      return stamina == 0;
    }

    /** Returns the name and statistics of the current fighter as a string. */
    @Override
    public String toString() {
      return name + " - " + type + " - skill: " + skill + "; stamina: " + stamina;
    }
  }

  /** Class that simulates battles between two fighters. */
  public static class GameEngine {

    /**
     * Simulates a battle between two fighters, printing each turn as it happens.
     *
     * @param first First fighter
     * @param second Second fighter
     */
    public static void simulateBattle(Fighter first, Fighter second) {
      System.out.printf("At the start of battle, stats are:\n%s\n%s%n", first, second);
      System.out.println("--------------------------------------------------");

      while (!first.isDead() && !second.isDead()) {
        int atk1 = first.calculateAttackScore();
        int atk2 = second.calculateAttackScore();

        if (atk1 > atk2) {
          second.takeDamage(first.calculateDamage());

          System.out.printf(
              "%s hits %s, stats are:\n%s\n%s\n", first.name, second.name, first, second);
        } else if (atk1 < atk2) {
          first.takeDamage(second.calculateDamage());

          System.out.printf(
              "%s hits %s, stats are:\n%s\n%s\n", second.name, first.name, second, first);
        } else {
          System.out.println(first.name + " draws with " + second.name + ".");
        }

        System.out.println("--------------------------------------------------");
      }

      Fighter winner = first.isDead() ? second : first;
      System.out.println("End of battle, " + winner + " wins!");
    }
  }
}
