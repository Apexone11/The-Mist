package world;

import characters.Dialogue;
import characters.NPC;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the collection of rooms that make up the game world.
 * Handles map loading from JSON and story/NPC initialization.
 */
public class WorldMap {
    private HashMap<String, Room> rooms;
    private String startRoomId;

    /**
     * Constructs an empty WorldMap.
     */
    public WorldMap() {
        rooms = new HashMap<>();
    }

    /**
     * Loads the map data from a JSON file.
     * @param filePath the path to the rooms.json file
     * @throws FileNotFoundException if the file is not found
     */
    public void loadMap(String filePath) throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(filePath);
        MapData data = gson.fromJson(reader, MapData.class);
        startRoomId = data.startRoom;

        for (Room room : data.rooms) {
            rooms.put(room.getId(), room);
        }

        // Initialize Story/NPCs
        initializeStory();
    }

    /**
     * Initializes the NPCs and their dialogues across the map.
     */
    private void initializeStory() {
        // R1: The Waking Shore - Spirit of the Mist
        Room r1 = rooms.get("R1");
        if (r1 != null) {
            NPC spirit = new NPC("N1", "Spirit of the Mist", "D1");
            
            Dialogue d1 = new Dialogue("D1", "Welcome, traveler. You have crossed the veil. The Mist consumes all, but you... you have a spark.");
            d1.addChoice("Who are you?", "D2", null);
            d1.addChoice("Where am I?", "D3", "QUEST_ADD:Survive the Mist");
            
            Dialogue d2 = new Dialogue("D2", "I am what remains of the first light. I am here to guide those who can still see.");
            d2.addChoice("Guide me then.", "D4", null);
            
            Dialogue d3 = new Dialogue("D3", "You are in Oakhaven, or what is left of it. The Mist Lord has draped this world in eternal grey.");
            d3.addChoice("How do I stop him?", "D4", null);
            
            Dialogue d4 = new Dialogue("D4", "To fight the shadows, you must choose your path. Will you embrace the Strength of the Ancients, or the Wisdom of the Stars?");
            d4.addChoice("I choose Strength.", "D5_STR", "ADD_STRENGTH:5");
            d4.addChoice("I choose Wisdom.", "D5_WIS", "ADD_MAGIC:5");
            
            Dialogue d5_str = new Dialogue("D5_STR", "Power flows through your veins. Go now, the watchtower awaits.");
            d5_str.addChoice("I am ready.", "END", "LEARN_SKILL:Power Strike:A heavy physical blow.:20:0:DAMAGE");
            
            Dialogue d5_wis = new Dialogue("D5_WIS", "Knowledge is your shield. Go now, and seek the truth.");
            d5_wis.addChoice("I am ready.", "END", null); // Mist Blast already learned in applyEffect
            
            spirit.addDialogue(d1);
            spirit.addDialogue(d2);
            spirit.addDialogue(d3);
            spirit.addDialogue(d4);
            spirit.addDialogue(d5_str);
            spirit.addDialogue(d5_wis);
            
            r1.addNPC(spirit);
        }

        // R3: The Broken Watchtower - Old Hermit
        Room r3 = rooms.get("R3");
        if (r3 != null) {
            NPC hermit = new NPC("N2", "Old Hermit", "H1");
            Dialogue h1 = new Dialogue("H1", "Cough... another soul lost in the grey? Listen closely, the woods to the east are whispering louder than usual.");
            h1.addChoice("What whispers?", "H2", null);
            
            Dialogue h2 = new Dialogue("H2", "The trees... they hunger for memories. Take this old charm, it might keep your mind whole.");
            h2.addChoice("Thank you.", "H3", "QUEST_ADD:Investigate the Whispers");
            
            Dialogue h3 = new Dialogue("H3", "Don't thank me yet. The Mist Lord's shadow grows long.");
            
            hermit.addDialogue(h1);
            hermit.addDialogue(h2);
            hermit.addDialogue(h3);
            r3.addNPC(hermit);
        }
    }

    /**
     * Retrieves a room by its ID.
     * @param id the unique room ID
     * @return the Room object, or null if not found
     */
    public Room getRoom(String id) {
        return rooms.get(id); // O(1) instant lookup!
    }

    /**
     * Gets the ID of the starting room.
     * @return the start room ID
     */
    public String getStartRoomId() {
        return startRoomId;
    }

    /**
     * Data structure for mapping JSON map data.
     */
    private class MapData {
        String startRoom;
        List<Room> rooms;
    }
}
