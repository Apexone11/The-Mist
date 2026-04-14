package world;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

public class WorldMap {
    private HashMap<String, Room> rooms;
    private String startRoomId;

    public WorldMap() {
        rooms = new HashMap<>();
    }

    public void loadMap(String filePath) throws FileNotFoundException {
        // Step 1: Create a Gson object
        Gson gson = new Gson();

        // Step 2: Open the file using a FileReader
        FileReader reader = new FileReader(filePath);

        // Step 3: Tell Gson to read the file into a MapData object
        MapData data = gson.fromJson(reader, MapData.class);

        // Step 4: Save the start room ID
        startRoomId = data.startRoom;

        // Step 5: Loop through each room and put it in the HashMap
        for (Room room : data.rooms) {
            rooms.put(room.getId(), room);
        }
    }

    public Room getRoom(String id) {
        return rooms.get(id); // O(1) instant lookup!
    }

    public String getStartRoomId() {
        return startRoomId;
    }

    // This is a private inner class inside WorldMap
    // Its ONLY job is to match the JSON structure so Gson can read it
    private class MapData {
        String startRoom;
        List<Room> rooms;
    }
}
