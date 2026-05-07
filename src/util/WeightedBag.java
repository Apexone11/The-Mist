package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A generic collection that allows for weighted random selection of items.
 * @param <T> the type of items in the bag
 */
public class WeightedBag<T> {
    /**
     * Internal class representing an item and its associated weight.
     */
    private class Entry {
        T item;
        int weight;

        /**
         * Constructs a new Entry.
         * @param item the item
         * @param weight the weight
         */
        Entry(T item, int weight) {
            this.item = item;
            this.weight = weight;
        }
    }

    private List<Entry> entries = new ArrayList<>();
    private int totalWeight = 0;
    private Random random = new Random();

    /**
     * Adds an item with a specific weight to the bag.
     * @param item the item to add
     * @param weight the weight of the item (must be positive)
     */
    public void add(T item, int weight) {
        if (weight <= 0) return;
        entries.add(new Entry(item, weight));
        totalWeight += weight;
    }

    /**
     * Selects a random item from the bag based on its weight.
     * @return a randomly selected item, or null if the bag is empty
     */
    public T getRandom() {
        if (totalWeight <= 0) return null;
        int roll = random.nextInt(totalWeight);
        int current = 0;
        for (Entry entry : entries) {
            current += entry.weight;
            if (roll < current) {
                return entry.item;
            }
        }
        return null;
    }

    /**
     * Removes all items from the bag.
     */
    public void clear() {
        entries.clear();
        totalWeight = 0;
    }
}
