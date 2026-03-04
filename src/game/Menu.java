/*
 * Abdul Rahman Fornah
 * aforna1@umbc.edu
 */
package game;

import exceptions.InvalidMenuChoiceException;
import io.InputUtil;

public class Menu {
    private final GameEngine gameEngine = new GameEngine();
    private boolean running = true;

    public void start() {
        while (running) {
            // Main menu loop; keep showing the banner and menu until quit.
            System.out.print("""
                    
                    === THE MIST ===
                    """);
            showMainMenu();
        }
    }

    private void showMainMenu() {
        System.out.println("""
                
                1) Start Game
                -------------
                2) Load Game
                -------------
                3) Settings
                -------------
                4) Quit
                """);
        handleMainMenuChoice();
    }

    private void handleMainMenuChoice() {
        try {
            int choice = InputUtil.getIntInput();
            // Dispatch the selected menu option.
            switch (choice) {
                case (1): {
                    gameEngine.startNewGame();
                    pressEnterToContinue();
                    gameEngine.runGameLoop();
                    break;
                }
                case (2): {
                    System.out.println("\n" + "Loading Game");
                    pressEnterToContinue();
                    break;
                }
                case (3): {
                    System.out.println("\n" + "Settings");
                    break;
                }
                case (4): {
                    System.out.println("\n" + "Thanks for playing The Mist.");
                    running = false;
                    break;
                }
                default: {
                    throw new InvalidMenuChoiceException("Please enter a valid number from (1-4).");
                }
            }
        } catch (InvalidMenuChoiceException e) {
            System.out.println(e.getMessage());
        }
    }

    private void pressEnterToContinue() {

        System.out.println("\n" + "press Enter to continue...");
        // Pause until the user presses Enter.
        InputUtil.waitForEnter();

    }
}
