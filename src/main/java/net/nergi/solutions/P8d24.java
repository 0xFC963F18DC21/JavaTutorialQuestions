package net.nergi.solutions;

import static net.nergi.Utils.listOf;
import static net.nergi.Utils.arrayListOf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.nergi.Solution;
import net.nergi.solutions.P7ec8.GameEngine;

@SuppressWarnings("unused")
public class P8d24 implements Solution {

  /**
   * Returns the header for the solution, which is the problem's name.
   */
  @Override
  public String getName() {
    return "8d24: Lucky battling fighters";
  }

  /**
   * Runs the solution to the problem.
   */
  @Override
  public void exec() {
    final Random usedRandom = new Random();

    ArrayList<String> possibleNames = arrayListOf("Alice", "Bob", "Charlie");
    ArrayList<String> possibleTypes = arrayListOf("Barbarian", "Mage", "Archer");

    final LuckyFighter fighter1 = new LuckyFighter(
        possibleNames.remove(usedRandom.nextInt(possibleNames.size())),
        possibleTypes.get(usedRandom.nextInt(possibleTypes.size())),
        usedRandom
    );

    final LuckyFighter fighter2 = new LuckyFighter(
        possibleNames.remove(usedRandom.nextInt(possibleNames.size())),
        possibleTypes.get(usedRandom.nextInt(possibleTypes.size())),
        usedRandom
    );

    GameEngine.simulateBattle(fighter1, fighter2);

  }

  private static class LuckyFighter extends P7ec8.Fighter {

    private static final int LUCK_UPPER_BOUND = 18;

    private static final List<FighterStyle> STYLES = listOf(FighterStyle.NORMAL,
        FighterStyle.AGGRESSIVE, FighterStyle.DEFENSIVE);

    private final FighterStyle style;

    private int luck;

    public LuckyFighter() {
      this("Unnamed", "Untyped", 1, 1, 1, new Random(), FighterStyle.NORMAL);
    }

    public LuckyFighter(String name, String type, int skill, int stamina, int luck, Random rnd,
        FighterStyle style) throws IllegalArgumentException {
      this.name = name;
      this.type = type;
      this.style = style;

      if (skill < 1 || skill > SKILL_UPPER_BOUND) {
        throw new IllegalArgumentException(
            "Skill initialiser out of bounds. Must be 1 <= skill <= " + SKILL_UPPER_BOUND + "."
        );
      } else {
        this.skill = skill;
      }

      if (stamina < 1 || stamina > STAMINA_UPPER_BOUND) {
        throw new IllegalArgumentException(
            "Stamina initialiser out of bounds. Must be 1 <= stamina <= " + STAMINA_UPPER_BOUND
                + "."
        );
      } else {
        this.stamina = stamina;
      }

      if (luck < 1 || luck > LUCK_UPPER_BOUND) {
        throw new IllegalArgumentException(
            "Luck initialiser out of bounds. Must be 1 <= luck <= " + LUCK_UPPER_BOUND + "."
        );
      } else {
        this.luck = luck;
      }

      this.actionRnd = rnd;
    }

    public LuckyFighter(String name, String type, Random generator) {
      this(name, type, generator.nextInt(SKILL_UPPER_BOUND) + 1,
          generator.nextInt(STAMINA_UPPER_BOUND) + 1,
          generator.nextInt(LUCK_UPPER_BOUND) + 1, generator,
          STYLES.get(generator.nextInt(STYLES.size())));
    }

    /**
     * Reduce the fighter's stamina by the amount of damage taken. The value will never go below
     * zero.
     *
     * @param damage Damage taken
     */
    @Override
    public void takeDamage(int damage) {
      if (luck >= 2 && actionRnd.nextDouble() <= style.useLuckDefendChance) {
        System.out.printf("%s tries to defend...\n", name);
        System.out.printf("%s tests luck...\n", name);

        if (actionRnd.nextInt(6) + actionRnd.nextInt(6) + 2 <= luck) {
          --luck;
          stamina = Math.max(0, stamina - (damage - 1));
          System.out.printf("%s resists some of the damage!\n", name);
        } else {
          --luck;
          stamina = Math.max(0, stamina - (damage + 1));
          System.out.printf("%s fails to defend and takes more damage!\n", name);
        }

        return;
      }
      stamina = Math.max(0, stamina - damage);
    }

    /**
     * Calculates the max amount of damage to send to the opponent.
     */
    @Override
    public int calculateDamage() {
      if (luck >= 2 && actionRnd.nextDouble() <= style.useLuckAttackChance) {
        System.out.printf("%s goes for a critical hit...\n", name);
        System.out.printf("%s tests luck...\n", name);

        if (actionRnd.nextInt(6) + actionRnd.nextInt(6) + 2 <= luck) {
          --luck;
          System.out.printf("%s rolls a critical hit!\n", name);
          return 4;
        } else {
          --luck;
          System.out.printf("%s fails to crit and their hit is weakened!\n", name);
          return 1;
        }
      }
      return 2;
    }

    /**
     * Calculates the attack score for the fighter's current turn.
     */
    @Override
    public int calculateAttackScore() {
      // Assuming 2d6
      return skill + (actionRnd.nextInt(6) + 1) + (actionRnd.nextInt(6) + 1);
    }

    @Override
    public boolean isDead() {
      return stamina == 0;
    }

    @Override
    public String toString() {
      return name + " - " + type + " - skill: " + skill + "; stamina: " + stamina + "; luck: "
          + luck;
    }
  }

  private enum FighterStyle {
    NORMAL(0.25, 0.25), AGGRESSIVE(0.1, 0.4), DEFENSIVE(0.4, 0.1);

    public double useLuckDefendChance;
    public double useLuckAttackChance;

    FighterStyle(double uldc, double ulac) {
      useLuckDefendChance = uldc;
      useLuckAttackChance = ulac;
    }
  }
}
