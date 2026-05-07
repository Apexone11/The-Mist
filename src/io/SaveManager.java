package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.GameState;
import java.io.*;

/**
 * Manages saving and loading the game state using JSON files.
 */
public class SaveManager {
    private static final String SAVE_FILE = "data/savegame.json";

    /**
     * Saves the current game state to a JSON file.
     * @param gameState the game state to save
     */
    public static void saveGame(GameState gameState) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            // We need to save the current room ID and player data
            // Since GameState holds references, Gson should handle most of it
            // But we might want to store room ID separately to re-link on load
            SaveData data = new SaveData();
            data.player = gameState.getPlayer();
            data.currentRoomId = gameState.getCurrentRoom().getId();
            
            gson.toJson(data, writer);
            System.out.println("Game saved successfully to " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Loads a game state from a save file.
     * @param worldMap the world map to re-link rooms with
     * @return the loaded GameState object
     * @throws FileNotFoundException if the save file does not exist or is corrupted
     */
    public static GameState loadGame(world.WorldMap worldMap) throws FileNotFoundException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(SAVE_FILE)) {
            SaveData data = gson.fromJson(reader, SaveData.class);
            GameState gameState = new GameState();
            gameState.setPlayer(data.player);
            gameState.setWorldMap(worldMap);
            gameState.setCurrentRoom(worldMap.getRoom(data.currentRoomId));
            return gameState;
        } catch (IOException e) {
            throw new FileNotFoundException("Save file not found or corrupted.");
        }
    }

    /**
     * Data structure for mapping JSON save data.
     */
    private static class SaveData {
        characters.Player player;
        String currentRoomId;
    }
}