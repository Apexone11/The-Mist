package characters;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Non-Player Character that the player can interact with through dialogue.
 */
public class NPC {
    private String id;
    private String name;
    private Map<String, Dialogue> dialogues;
    private String startDialogueId;

    /**
     * Constructs a new NPC.
     * @param id the unique identifier for the NPC
     * @param name the name of the NPC
     * @param startDialogueId the ID of the dialogue to start with
     */
    public NPC(String id, String name, String startDialogueId) {
        this.id = id;
        this.name = name;
        this.startDialogueId = startDialogueId;
        this.dialogues = new HashMap<>();
    }

    /**
     * Gets the NPC's ID.
     * @return the ID
     */
    public String getId() { return id; }

    /**
     * Gets the NPC's name.
     * @return the name
     */
    public String getName() { return name; }

    /**
     * Adds a dialogue entry to the NPC's repertoire.
     * @param dialogue the dialogue to add
     */
    public void addDialogue(Dialogue dialogue) {
        dialogues.put(dialogue.getId(), dialogue);
    }

    /**
     * Retrieves a dialogue entry by its ID.
     * @param id the dialogue ID
     * @return the Dialogue object, or null if not found
     */
    public Dialogue getDialogue(String id) {
        return dialogues.get(id);
    }

    /**
     * Gets the ID of the initial dialogue for this NPC.
     * @return the start dialogue ID
     */
    public String getStartDialogueId() {
        return startDialogueId;
    }
}
