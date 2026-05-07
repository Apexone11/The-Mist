package characters;

/**
 * Represents a monster that the player can encounter and fight.
 */
public class Monster {
    private String name;
    private int hp;
    private int maxHp;
    private int strength;
    private int defense;
    private int xpReward;

    /**
     * Constructs a new Monster with the specified stats.
     * @param name the name of the monster
     * @param hp the starting health points
     * @param strength the attack power
     * @param defense the defensive capability
     * @param xpReward the experience points awarded upon defeat
     */
    public Monster(String name, int hp, int strength, int defense, int xpReward) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.strength = strength;
        this.defense = defense;
        this.xpReward = xpReward;
    }

    /**
     * Gets the name of the monster.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current health points of the monster.
     * @return the current HP
     */
    public int getHp() {
        return hp;
    }

    /**
     * Sets the current health points of the monster.
     * @param hp the HP to set
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Gets the maximum health points of the monster.
     * @return the max HP
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Gets the strength (attack power) of the monster.
     * @return the strength
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Gets the defense of the monster.
     * @return the defense
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Gets the experience reward for defeating this monster.
     * @return the XP reward
     */
    public int getXpReward() {
        return xpReward;
    }
}
