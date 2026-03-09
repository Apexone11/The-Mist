package game;

import characters.Player;
import exceptions.InvalidMenuChoiceException;

public class GameState {
    private GameState(){
    }
     public static void playerCreation() throws InvalidMenuChoiceException {
        Player player = Player.playerCreation();
     }
}
