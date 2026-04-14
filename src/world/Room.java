package world;


import java.util.HashMap;


public class Room {
    private String id;
    private String name;
    private String description;
    private HashMap<String,String>neighbors;
    private double encounterRate;

    Room(){
        neighbors = new HashMap<>();
        encounterRate = 0;
        id = "";
        name = "";
        description = "";
    }

    public void addNeighbor(String direction, String roomId){
        neighbors.put(direction.toUpperCase(), roomId);
    }
    public void setEncounterRate(double encounterRate){
        this.encounterRate = encounterRate;
    }
    public double getEncounterRate(){
        return encounterRate;
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public boolean hasNeighbor(String direction){
        return neighbors.containsKey(direction.toUpperCase());
    }
    public String getNeighborId(String direction){
        return neighbors.get(direction.toUpperCase());
    }

    public HashMap<String, String> getNeighbors() {
        return neighbors;
    }
}
