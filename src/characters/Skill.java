package characters;

/**
 * Represents a special ability that the player can learn and use in combat.
 */
public class Skill {
    private String name;
    private String description;
    private int power;
    private int cost;
    private String type; // "DAMAGE", "HEAL", "BUFF"

    /**
     * Constructs a new Skill.
     * @param name the name of the skill
     * @param description a description of the skill's effect
     * @param power the power value (e.g., damage or healing amount)
     * @param cost the cost to use the skill (e.g., MP - currently unused)
     * @param type the type of skill ("DAMAGE", "HEAL", or "BUFF")
     */
    public Skill(String name, String description, int power, int cost, String type) {
        this.name = name;
        this.description = description;
        this.power = power;
        this.cost = cost;
        this.type = type;
    }

    /**
     * Gets the name of the skill.
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Gets the skill's description.
     * @return the description
     */
    public String getDescription() { return description; }

    /**
     * Gets the power value of the skill.
     * @return the power
     */
    public int getPower() { return power; }

    /**
     * Gets the cost to use the skill.
     * @return the cost
     */
    public int getCost() { return cost; }

    /**
     * Gets the type of the skill.
     * @return the type
     */
    public String getType() { return type; }
}
