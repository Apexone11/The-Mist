package characters;

public class Monster {
    private String name;
    private int hp;
    private int maxHp;
    private int strength;
    private int defense;
    private int xpReward;

    public Monster(String name, int hp, int strength, int defense, int xpReward) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.strength = strength;
        this.defense = defense;
        this.xpReward = xpReward;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefense() {
        return defense;
    }

    public int getXpReward() {
        return xpReward;
    }
}
