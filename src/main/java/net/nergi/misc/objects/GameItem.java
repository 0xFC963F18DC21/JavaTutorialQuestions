package net.nergi.misc.objects;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * Immutable class that holds information about about a generic item that would be in a game. Uses
 * factory methods to create items without consuming too much memory.
 */
@SuppressWarnings("unused")
public final class GameItem {

  /** Weak map holding references to items. */
  private static final WeakHashMap<GameItem, WeakReference<GameItem>> createdItems =
      new WeakHashMap<>();

  private final String name;
  private final String description;

  /** Private constructor to prevent leaking memory by creating redundant references. */
  private GameItem(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Create a new game item with the given name and description.
   *
   * @param name Name of item
   * @param description Description of item
   * @return Game item with given parameters.
   */
  public static GameItem makeItem(String name, String description) {
    final GameItem current = new GameItem(name, description);
    final WeakReference<GameItem> ref = createdItems.get(current);

    if (ref == null) {
      createdItems.put(current, new WeakReference<>(current));
      return current;
    } else {
      return ref.get();
    }
  }

  /** Get item name. */
  public String getName() {
    return name;
  }

  /** Get item description. */
  public String getDescription() {
    return description;
  }

  /**
   * A <code>GameItem</code> is only equal to another object iff the object is another <code>
   * GameItem</code> and the name and description matches.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final GameItem gameItem = (GameItem) o;
    return name.equals(gameItem.name) && description.equals(gameItem.description);
  }

  /** Get item hash using default hashing algorithm. */
  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }

  /** Gets a string representation of the current item. */
  @Override
  public String toString() {
    return name + ": " + description;
  }
}
