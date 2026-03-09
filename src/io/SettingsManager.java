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
                    throw new InvalidMenuChoiceException("Difficulty Settings", 1, 3, difficultyChoice);
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
                    throw new InvalidMenuChoiceException("Show Combat Log", 1, 2, showCombatLogChoice);
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
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                String difficultyValue = null;
                String showCombatLogValue = null;

                int diffKeyIndex = trimmed.indexOf("\"difficulty\"");
                if (diffKeyIndex >= 0) {
                    int colon = trimmed.indexOf(":", diffKeyIndex);
                    int firstQuote = trimmed.indexOf("\"", colon + 1);
                    int secondQuote = trimmed.indexOf("\"", firstQuote + 1);
                    if (firstQuote >= 0 && secondQuote > firstQuote) {
                        difficultyValue = trimmed.substring(firstQuote + 1, secondQuote);
                    }
                }

                int showCombatLogKeyIndex = trimmed.indexOf("\"showCombatLog\"");
                if (showCombatLogKeyIndex >= 0) {
                    int colon = trimmed.indexOf(":", showCombatLogKeyIndex);
                    int comma = trimmed.indexOf(",", colon + 1);
                    int brace = trimmed.indexOf("}", colon + 1);
                    int valueEnd = (comma >= 0) ? comma : brace;
                    if (colon >= 0 && valueEnd > colon) {
                        showCombatLogValue = trimmed.substring(colon + 1, valueEnd).trim();
                    }
                }

                if (difficultyValue != null) {
                    System.out.println("\n" + "difficulty: " + difficultyValue);
                }
                if (showCombatLogValue != null) {
                    System.out.println("showCombatLog: " + showCombatLogValue);
                }
                if (difficultyValue == null && showCombatLogValue == null) {
                    System.out.println("\n" + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFile() throws NullPointerException {

        System.out.println("Writing to file...");
        try (BufferedWriter b = new BufferedWriter(new FileWriter(SETTINGS_FILE_PATH.toFile()))) {
            String settings = "{\"difficulty\":\"" + difficulty + "\",\"showCombatLog\":" + showCombatLog + "}";
            b.write(settings);
            b.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
