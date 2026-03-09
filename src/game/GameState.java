package game;

import characters.Player;
import exceptions.InvalidMenuChoiceException;

public class GameState {

    GameState() throws InvalidMenuChoiceException {
        Player player = Player.playerCreation();
    }
}