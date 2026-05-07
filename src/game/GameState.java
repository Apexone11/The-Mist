package game;

import characters.Player;
import exceptions.InvalidMenuChoiceException;
import world.Room;
import world.WorldMap;

/**
 * Represents the current state of the game, including the player, the world map, and the current location.
 */
public class GameState {
    private Player player;
    private WorldMap worldMap;
    private Room currentRoom;

    /**
     * Constructs a new empty GameState.
     */
    public GameState() {
    }

    /**
     * Gets the current player.
     * @return the player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the current player.
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the current world map.
     * @return the world map
     */
    public WorldMap getWorldMap() {
        return worldMap;
    }

    /**
     * Sets the current world map.
     * @param worldMap the world map to set
     */
    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    /**
     * Gets the room the player is currently in.
     * @return the current room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Sets the room the player is currently in.
     * @param currentRoom the room to set as current
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Initiates the player creation process and updates the game state.
     * @param state the current game state to update
     * @throws InvalidMenuChoiceException if an invalid choice is made during creation
     */
    public static void playerCreation(GameState state) throws InvalidMenuChoiceException {
        state.setPlayer(Player.playerCreation());
    }
}
