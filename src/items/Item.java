package items;

/**
 * Abstract base class for all items in the game.
 */
public abstract class Item {
    private String name;
    private String description;
    private String type;
    private int value;

    /**
     * Constructs a new Item.
     * @param name the name of the item
     * @param description a brief description of the item
     * @param type the category of the item (e.g., "WEAPON", "POTION")
     * @param value the worth or power value of the item
     */
    public Item(String name, String description, String type, int value) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    /**
     * Gets the name of the item.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the item.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the type category of the item.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the value of the item.
     * @return the value
     */
    public int getValue() {
        return value;
    }
}
