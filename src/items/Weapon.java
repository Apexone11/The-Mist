package items;

import characters.Player;

public class Weapon extends Item {
    private int attackBonus;

    public Weapon(String name, String description, int attackBonus) {
        super(name, description, "WEAPON", attackBonus);
        this.attackBonus = attackBonus;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void equip(Player player) {
        // In a real equipment system, we would track equipped items in Player
        // For now, we just acknowledge the weapon exists in inventory and is used in CombatEngine
        System.out.println("You are now using your " + getName() + "! Your total strength is " + (player.getStrength() + attackBonus) + ".");
    }
}
