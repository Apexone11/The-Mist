package items;

import characters.Player;

/**
 * Represents a consumable potion that restores health to the player.
 */
public class Potion extends Item {
    /**
     * Constructs a new Potion.
     * @param name the name of the potion
     * @param description the description of the potion
     * @param value the amount of HP restored by the potion
     */
    public Potion(String name, String description, int value) {
        super(name, description, "POTION", value);
    }

    /**
     * Uses the potion on the specified player, restoring their HP.
     * @param player the player to use the potion on
     */
    public void use(Player player) {
        int newHp = player.getHp() + getValue();
        if (newHp > player.getMaxHp()) {
            newHp = player.getMaxHp();
        }
        player.setHp(newHp);
        System.out.println("You used " + getName() + "! Restored " + getValue() + " HP.");
    }
}
