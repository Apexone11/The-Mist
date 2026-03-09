package characters;

import exceptions.InvalidMenuChoiceException;
import io.InputUtil;

public class Player {
    // Persistent player progression values.
    private final int xp = 0;
    private final int level = 1;

    // Per-player stats (instance fields, not shared across players).
    private int hp = 100;
    private int maxHp = 100;
    private int strength = 0;
    private int speed = 0;
    private int defense = 0;
    private int magic = 0;
    private String name = "Player";

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
    public int addXp(int xp) {
        return xp + xp;
    }
    Player() {
    }

    public static Player playerCreation() throws InvalidMenuChoiceException {
        // Create a fresh player instance to populate with input.
        Player player = new Player();

        System.out.println("Player creation");

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
        } catch (InvalidMenuChoiceException e) {
            System.out.println(e.getMessage());
            return playerCreation();
        }
        if (choice >= 1 && choice <= 3) {
            // Apply the chosen class stats to the player.
            int idx = choice - 1;
            player.hp = classStats[idx][0];
            player.maxHp = classStats[idx][0];
            player.strength = classStats[idx][1];
            player.defense = classStats[idx][2];
            player.magic = classStats[idx][3];
            player.speed = classStats[idx][4];
        }
        System.out.println("\n" + "------------------------------");

        // Show the final player summary after creation.
        System.out.println("Welcome to The Mist, " + player.name + "!" + "\n" + "HP" + player.hp + "/" + player.maxHp + "\n" + "Strength: " + player.strength + "\n" + "Defense: " + player.defense + "\n" + "Magic: " + player.magic + "\n" + "Speed: " + player.speed);

        return player;
    }

}
