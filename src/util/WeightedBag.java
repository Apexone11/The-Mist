package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedBag<T> {
    private class Entry {
        T item;
        int weight;

        Entry(T item, int weight) {
            this.item = item;
            this.weight = weight;
        }
    }

    private List<Entry> entries = new ArrayList<>();
    private int totalWeight = 0;
    private Random random = new Random();

    public void add(T item, int weight) {
        if (weight <= 0) return;
        entries.add(new Entry(item, weight));
        totalWeight += weight;
    }

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

    public void clear() {
        entries.clear();
        totalWeight = 0;
    }
}
