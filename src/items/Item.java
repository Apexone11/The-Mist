package items;

public abstract class Item {
    private String name;
    private String description;
    private String type;
    private int value;

    public Item(String name, String description, String type, int value) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
