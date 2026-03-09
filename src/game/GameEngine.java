/*
 * Abdul Rahman Fornah
 * aforna1@umbc.edu
 */
package game;

import characters.Player;
import exceptions.InvalidMenuChoiceException;
import io.InputUtil;

public class GameEngine {

    GameEngine() {

    }

    public void startNewGame() throws InvalidMenuChoiceException {
        // Collect the player name before creating the Player.
        GameState gameState = new GameState();
    }

    public void runGameLoop() {
        boolean inGame = true;
        int choice;

        while (inGame) {
            // In-game menu loop.
            System.out.println("""
                    
                    1)  Explore / Move (placeholder)
                    
                    2)  Character Sheet (placeholder)
                    
                    3)  Inventory (placeholder)
                    
                    4)  Save (placeholder for Week 9)
                    
                    5)  Quit to Main Menu
                    """);

            try {
                choice = InputUtil.getIntInput();
                switch (choice) {
                    case (1): {
                        // Placeholder for movement.
                        System.out.println("\n" + "Exploration coming Week 4 ");
                        break;
                    }
                    case (2): {
                        // Placeholder for character sheet.
                        System.out.println("\n" + "Character sheet coming Week 3");
                        break;
                    }
                    case (3): {
                        // Placeholder for inventory.
                        System.out.println("\n" + "Inventory coming Week 8");
                        break;
                    }
                    case (4): {
                        // Placeholder for save.
                        System.out.println("\n" + "Save coming Week 9");
                        break;
                    }
                    case (5): {
                        inGame = false;
                        break;
                    }
                    default: {
                        throw new InvalidMenuChoiceException("Please enter a valid number from (1-5).");
                    }
                }
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
