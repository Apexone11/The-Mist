package characters;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single piece of dialogue in a branching conversation.
 */
public class Dialogue {
    private String id;
    private String text;
    private List<Choice> choices;

    /**
     * Constructs a new Dialogue entry.
     * @param id the unique ID of the dialogue
     * @param text the text to display to the player
     */
    public Dialogue(String id, String text) {
        this.id = id;
        this.text = text;
        this.choices = new ArrayList<>();
    }

    /**
     * Gets the dialogue's ID.
     * @return the ID
     */
    public String getId() { return id; }

    /**
     * Gets the dialogue text.
     * @return the text
     */
    public String getText() { return text; }

    /**
     * Gets the list of choices available to the player.
     * @return the list of choices
     */
    public List<Choice> getChoices() { return choices; }

    /**
     * Adds a branching choice to this dialogue.
     * @param text the choice text
     * @param nextDialogueId the ID of the dialogue that follows this choice
     * @param effect the effect string to apply when this choice is selected
     */
    public void addChoice(String text, String nextDialogueId, String effect) {
        choices.add(new Choice(text, nextDialogueId, effect));
    }

    /**
     * Represents a player's choice within a dialogue.
     */
    public static class Choice {
        private String text;
        private String nextDialogueId;
        private String effect; // e.g., "ADD_STRENGTH:5", "LEARN_SKILL:Heal"

        /**
         * Constructs a new Choice.
         * @param text the text for the choice
         * @param nextDialogueId the ID of the next dialogue
         * @param effect the effect of selecting this choice
         */
        public Choice(String text, String nextDialogueId, String effect) {
            this.text = text;
            this.nextDialogueId = nextDialogueId;
            this.effect = effect;
        }

        /**
         * Gets the choice text.
         * @return the text
         */
        public String getText() { return text; }

        /**
         * Gets the ID of the next dialogue.
         * @return the next dialogue ID
         */
        public String getNextDialogueId() { return nextDialogueId; }

        /**
         * Gets the effect of this choice.
         * @return the effect string
         */
        public String getEffect() { return effect; }
    }
}
