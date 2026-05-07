package items;

import characters.Player;

/**
 * Represents a weapon item that increases the player's attack power.
 */
public class Weapon extends Item {
    private int attackBonus;

    /**
     * Constructs a new Weapon.
     * @param name the name of the weapon
     * @param description the description of the weapon
     * @param attackBonus the bonus damage added to attacks
     */
    public Weapon(String name, String description, int attackBonus) {
        super(name, description, "WEAPON", attackBonus);
        this.attackBonus = attackBonus;
    }

    /**
     * Gets the attack bonus of this weapon.
     * @return the attack bonus
     */
    public int getAttackBonus() {
        return attackBonus;
    }

    /**
     * Equips the weapon to the specified player.
     * @param player the player to equip the weapon to
     */
    public void equip(Player player) {
        // In a real equipment system, we would track equipped items in Player
        // For now, we just acknowledge the weapon exists in inventory and is used in CombatEngine
        System.out.println("You are now using your " + getName() + "! Your total strength is " + (player.getStrength() + attackBonus) + ".");
    }
}
