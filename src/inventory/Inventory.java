package inventory;

import items.Item;
import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

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

    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }
}
