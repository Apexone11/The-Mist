package items;

import characters.Player;

public class Potion extends Item {
    public Potion(String name, String description, int value) {
        super(name, description, "POTION", value);
    }

    public void use(Player player) {
        int newHp = player.getHp() + getValue();
        if (newHp > player.getMaxHp()) {
            newHp = player.getMaxHp();
        }
        player.setHp(newHp);
        System.out.println("You used " + getName() + "! Restored " + getValue() + " HP.");
    }
}
