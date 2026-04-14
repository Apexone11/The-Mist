package characters;

import exceptions.InvalidMenuChoiceException;
import inventory.Inventory;
import io.InputUtil;
import items.Potion;

public class Player {
    // Per-player stats (instance fields, not shared across players).
    private int hp = 100;
    private int maxHp = 100;
    private int strength = 0;
    private int speed = 0;
    private int defense = 0;
    private int magic = 0;
    private String name = "Player";
    private int xp = 0;
    private int level = 1;
    private Inventory inventory;

    public Player() {
        this.inventory = new Inventory();
    }

    public static Player playerCreation() throws InvalidMenuChoiceException {
        // Create a fresh player instance to populate with input.
        Player player = new Player();

        System.out.println("\n" + "Player creation");

        // Capture the player's name.
        System.out.print("\n" + "Enter players name: ");
        player.name = InputUtil.getStringInput();
        System.out.println("\n" + "------------------------------");

        // Show available classes.
        System.out.println("\n" + "Choose your class:");
        System.out.println("1) Warrior" + "(high HP, high defense, low magic , low speed)");
        System.out.println("2) Mage" + "(low HP,high magic, high defense, low speed)");
        System.out.println("3) Rogue" + "(low HP,low magic, high Strength, high speed)");

        // Class table for consistent stat assignment.
        String[] classNames = {"Warrior", "Mage", "Rogue"};
        // Stats order: HP, STR, DEF, MAG, SPD.
        int[][] classStats = {
                {130, 12, 16, 3, 5},  // Warrior
                {90, 4, 8, 18, 6},    // Mage
                {100, 16, 6, 4, 10}   // Rogue
        };

        int choice;
        try {
            // Read the class choice from the player.
            System.out.print("\n");
            choice = InputUtil.getIntInput();
            if (choice < 1 || choice > 3) {
                throw new InvalidMenuChoiceException("Class Selection", 1, 3, choice);
            }
        } catch (InvalidMenuChoiceException e) {
            System.out.println(e.getMessage());
            return playerCreation();
        }
        // Apply the chosen class stats to the player.
        int idx = choice - 1;
        player.hp = classStats[idx][0];
        player.maxHp = classStats[idx][0];
        player.strength = classStats[idx][1];
        player.defense = classStats[idx][2];
        player.magic = classStats[idx][3];
        player.speed = classStats[idx][4];

        // Add starter potions
        player.getInventory().addItem(new Potion("Health Potion", "Restores 30 HP", 30));
        player.getInventory().addItem(new Potion("Health Potion", "Restores 30 HP", 30));

        System.out.println("\n" + "------------------------------");

        // Show the final player summary after creation.
        System.out.println("Welcome to The Mist, " + player.name + "!" + "\n" + "HP" + player.hp + "/" + player.maxHp + "\n" + "Strength: " + player.strength + "\n" + "Defense: " + player.defense + "\n" + "Magic: " + player.magic + "\n" + "Speed: " + player.speed);

        return player;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getStrength() {
        return strength;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDefense() {
        return defense;
    }

    public int getMagic() {
        return magic;
    }

    public String getName() {
        return name;
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void addXp(int amount) {
        this.xp += amount;
        if (this.xp >= level * 100) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        xp = 0;
        maxHp += 10;
        hp = maxHp;
        strength += 2;
        defense += 2;
        System.out.println("\nLEVEL UP! You are now level " + level);
        System.out.println("HP: " + hp + " | STR: " + strength + " | DEF: " + defense);
    }
}
