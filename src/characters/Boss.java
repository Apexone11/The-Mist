package characters;

/**
 * Represents a powerful monster with a unique quote.
 */
public class Boss extends Monster {
    private String bossQuote;

    /**
     * Constructs a new Boss with the specified stats and quote.
     * @param name the name of the boss
     * @param hp the starting health points
     * @param strength the attack power
     * @param defense the defensive capability
     * @param xpReward the experience points awarded upon defeat
     * @param bossQuote a unique quote the boss says
     */
    public Boss(String name, int hp, int strength, int defense, int xpReward, String bossQuote) {
        super(name, hp, strength, defense, xpReward);
        this.bossQuote = bossQuote;
    }

    /**
     * Gets the boss's unique quote.
     * @return the quote
     */
    public String getBossQuote() {
        return bossQuote;
    }
}
