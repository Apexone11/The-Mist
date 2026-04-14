package game;

import characters.Player;
import exceptions.InvalidMenuChoiceException;
import world.Room;
import world.WorldMap;

public class GameState {
    private Player player;
    private WorldMap worldMap;
    private Room currentRoom;

    public GameState() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public static void playerCreation(GameState state) throws InvalidMenuChoiceException {
        state.setPlayer(Player.playerCreation());
    }
}
