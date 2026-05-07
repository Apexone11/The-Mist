/*
 * Abdul Rahman Fornah
 * aforna1@umbc.edu
 */
package game;

import exceptions.InvalidMenuChoiceException;
import io.InputUtil;
import io.SettingsManager;
import util.ConsoleUI;

/**
 * The main menu controller for the game.
 * Handles the initial options such as starting a new game, loading a game, and settings.
 */
public class Menu {
    private final GameEngine gameEngine = new GameEngine();
    private final SettingsManager settingsManager = new SettingsManager();

    private boolean running = true;

    /**
     * Starts the main menu loop.
     */
    public void start() {
        while (running) {
            ConsoleUI.printHeader("The Mist");
            showMainMenu();
        }
    }

    /**
     * Displays the main menu options to the console.
     */
    private void showMainMenu() {
        System.out.println("""
                
                   [1]  Start New Adventure
                   [2]  Continue Journey
                   [3]  Settings
                   [4]  Exit to Void
                """);
        ConsoleUI.printDivider();
        handleMainMenuChoice();
    }

    /**
     * Processes the user's input for the main menu.
     */
    private void handleMainMenuChoice() {
        try {
            int choice = InputUtil.getIntInput();
            // Dispatch the selected menu option.
            switch (choice) {
                case (1): {
                    gameEngine.startNewGame();
                    pressEnterToContinue();
                    break;
                }
                case (2): {
                    gameEngine.loadExistingGame();
                    pressEnterToContinue();
                    break;
                }
                case (3): {
                    System.out.println("\n" + "Settings");
                    SettingsManager.setting();
                    break;
                }
                case (4): {
                    System.out.println("\n" + "Thanks for playing The Mist.");
                    running = false;
                    break;
                }
                default: {
                    throw new InvalidMenuChoiceException("Main Menu", 1, 4, choice);
                }
            }
        } catch (InvalidMenuChoiceException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Pauses the execution and waits for the user to press Enter.
     */
    private void pressEnterToContinue() {

        System.out.println("\n" + "press Enter to continue...");
        // Pause until the user presses Enter.
        InputUtil.waitForEnter();

    }
}
