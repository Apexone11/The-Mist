package inventory;

import items.Item;
import java.util.ArrayList;

/**
 * Represents the player's inventory, managing a collection of items.
 */
public class Inventory {
    private ArrayList<Item> items;

    /**
     * Constructs an empty Inventory.
     */
    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the inventory.
     * @param item the item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the inventory.
     * @param item the item to remove
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Gets the list of items in the inventory.
     * @return the list of items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Displays all items in the inventory to the console.
     */
    public void displayItems() {
        System.out.println("==============================");
        System.out.println("  🎒 INVENTORY");
        System.out.println("==============================");
        if (items.isEmpty()) {
            System.out.println("(empty)");
        } else {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                System.out.println((i + 1) + ") " + item.getName() + " → " + item.getDescription());
            }
        }
    }

    /**
     * Retrieves an item from the inventory by its index.
     * @param index the index of the item
     * @return the item at the specified index, or null if the index is invalid
     */
    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }
}
