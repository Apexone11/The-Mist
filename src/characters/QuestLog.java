package characters;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the player's active and completed quests.
 */
public class QuestLog {
    private List<String> activeQuests;
    private List<String> completedQuests;

    /**
     * Constructs a new empty QuestLog.
     */
    public QuestLog() {
        this.activeQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
    }

    /**
     * Adds a new quest to the log if it's not already present or completed.
     * @param quest the name of the quest to add
     */
    public void addQuest(String quest) {
        if (!activeQuests.contains(quest) && !completedQuests.contains(quest)) {
            activeQuests.add(quest);
        }
    }

    /**
     * Marks an active quest as completed.
     * @param quest the name of the quest to complete
     */
    public void completeQuest(String quest) {
        if (activeQuests.remove(quest)) {
            completedQuests.add(quest);
        }
    }

    /**
     * Gets the list of active quests.
     * @return a list of active quest names
     */
    public List<String> getActiveQuests() { return activeQuests; }

    /**
     * Gets the list of completed quests.
     * @return a list of completed quest names
     */
    public List<String> getCompletedQuests() { return completedQuests; }
}
