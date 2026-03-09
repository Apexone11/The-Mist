package io;

import exceptions.InvalidMenuChoiceException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SettingsManager {
    private static final Path SETTINGS_FILE_PATH = Paths.get("settings", "settings.json");
    private static String difficulty = "Medium";
    private static boolean showCombatLog = true;

    public static Path getSettingsFilePath() {
        return SETTINGS_FILE_PATH;
    }

    public static String getDifficulty() {
        return difficulty;
    }

    public static boolean isShowCombatLog() {
        return showCombatLog;
    }

    public static void setting() {
        System.out.println("\n");
        writeFile();
        System.out.println("\n" + "Settings initialized");

        System.out.println("""
                
                1) Difficulty
                
                2) Show Combat Log
                
                3) Current Settings
                
                4) Reset Settings
                """);

        int choose;
        try {
            choose = InputUtil.getIntInput();
        } catch (InvalidMenuChoiceException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (choose == 1) {
            System.out.println("""
                    Difficulty settings:
                    
                    1) Easy
                    
                    2) Medium
                    
                    3) Hard
                    """);

            try {
                int difficultyChoice = InputUtil.getIntInput();
                if (difficultyChoice == 1) {
                    difficulty = "Easy";
                    writeFile();
                } else if (difficultyChoice == 2) {
                    difficulty = "Medium";
                    writeFile();
                } else if (difficultyChoice == 3) {
                    difficulty = "Hard";
                    writeFile();
                } else {
                    throw new InvalidMenuChoiceException("Please enter a valid number from (1-3).");
                }
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            }
        } else if (choose == 2) {
            System.out.println("""
                    Show Combat Log settings:
                    
                    1) Yes
                    
                    2) No
                    """);

            try {
                int showCombatLogChoice = InputUtil.getIntInput();
                if (showCombatLogChoice == 1) {
                    showCombatLog = true;
                    writeFile();
                } else if (showCombatLogChoice == 2) {
                    showCombatLog = false;
                    writeFile();
                } else {
                    throw new InvalidMenuChoiceException("Please enter a valid number from (1-2).");
                }
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            }
        } else if (choose == 3) {
            System.out.println("\n" + "Current Settings:");
            readFile();
        } else if (choose == 4) {
            resetSettings();
            System.out.println("\n" + "Settings reset to default values.");
        } else {
            System.out.println("Please enter a valid number from (1-4).");
        }
    }

    public static void resetSettings() {
        difficulty = "Medium";
        showCombatLog = true;
        writeFile();
    }

    public static void readFile() throws NullPointerException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(SETTINGS_FILE_PATH.toFile()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("\n" + line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFile() throws NullPointerException {

        System.out.println("Writing to file...");
        try (BufferedWriter b = new BufferedWriter(new FileWriter(SETTINGS_FILE_PATH.toFile()))) {
            String settings = "Difficulty: " + difficulty + "\n" + "Show Combat Log: " + showCombatLog;
            b.write(settings);
            b.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
