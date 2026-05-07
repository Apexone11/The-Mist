package world;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import characters.NPC;

/**
 * Represents a single room or location in the game world.
 * Each room has a name, description, neighbors, and potential NPC inhabitants.
 */
public class Room {
    private String id;
    private String name;
    private String description;
    private HashMap<String,String>neighbors;
    private double encounterRate;
    private List<NPC> npcs;

    /**
     * Constructs a new Room with default values.
     */
    Room(){
        neighbors = new HashMap<>();
        encounterRate = 0;
        id = "";
        name = "";
        description = "";
        npcs = new ArrayList<>();
    }

    /**
     * Adds an NPC to the room.
     * @param npc the NPC to add
     */
    public void addNPC(NPC npc) {
        npcs.add(npc);
    }

    /**
     * Gets the list of NPCs in this room.
     * @return the list of NPCs
     */
    public List<NPC> getNpcs() {
        return npcs;
    }

    /**
     * Adds a neighbor connection to another room.
     * @param direction the direction of the neighbor (e.g., "N", "S")
     * @param roomId the ID of the neighboring room
     */
    public void addNeighbor(String direction, String roomId){
        neighbors.put(direction.toUpperCase(), roomId);
    }

    /**
     * Sets the encounter rate for random monsters in this room.
     * @param encounterRate the rate between 0 and 1
     */
    public void setEncounterRate(double encounterRate){
        this.encounterRate = encounterRate;
    }

    /**
     * Gets the encounter rate for this room.
     * @return the encounter rate
     */
    public double getEncounterRate(){
        return encounterRate;
    }

    /**
     * Gets the unique ID of the room.
     * @return the room ID
     */
    public String getId(){
        return id;
    }

    /**
     * Gets the name of the room.
     * @return the room name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the description of the room.
     * @return the room description
     */
    public String getDescription(){
        return description;
    }

    /**
     * Checks if the room has a neighbor in the specified direction.
     * @param direction the direction to check
     * @return true if a neighbor exists, false otherwise
     */
    public boolean hasNeighbor(String direction){
        return neighbors.containsKey(direction.toUpperCase());
    }

    /**
     * Gets the ID of the neighboring room in the specified direction.
     * @param direction the direction to check
     * @return the ID of the neighbor, or null if none
     */
    public String getNeighborId(String direction){
        return neighbors.get(direction.toUpperCase());
    }

    /**
     * Gets all neighboring room connections.
     * @return a map of direction to room ID
     */
    public HashMap<String, String> getNeighbors() {
        return neighbors;
    }
}
