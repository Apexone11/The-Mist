package characters;

public class Boss extends Monster {
    private String bossQuote;

    public Boss(String name, int hp, int strength, int defense, int xpReward, String bossQuote) {
        super(name, hp, strength, defense, xpReward);
        this.bossQuote = bossQuote;
    }

    public String getBossQuote() {
        return bossQuote;
    }
}
