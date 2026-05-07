/*
 * Abdul Rahman Fornah
 * aforna1@umbc.edu
 */
package io;

import exceptions.InvalidMenuChoiceException;

import java.util.Scanner;

/**
 * Utility class for handling user input from the console.
 */
public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads an integer from the console.
     * @return the integer entered by the user
     * @throws InvalidMenuChoiceException if the input is not a valid integer
     */
    public static int getIntInput() throws InvalidMenuChoiceException {
        // Read a whole line to avoid Scanner nextInt/nextLine mixing issues.
        String line = scanner.nextLine();
        if (line == null || line.trim().isEmpty()) {
            throw new InvalidMenuChoiceException("Please enter a number.", line);
        }
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            throw new InvalidMenuChoiceException("Please enter a number.", line);
        }
    }


    /**
     * Reads a non-empty string from the console.
     * @return the trimmed string entered by the user
     * @throws NullPointerException if the input is empty or null
     */
    public static String getStringInput() throws NullPointerException {
        // Return a non-empty trimmed string from user input.
        String line = scanner.nextLine();
        if (line == null || line.trim().isEmpty()) {
            throw new NullPointerException();
        }
        try {
            return line.trim();
        } catch (NullPointerException e) {
            throw new NullPointerException(" Please enter valid text");
        }
    }

    /**
     * Pauses the execution until the user presses Enter.
     */
    public static void waitForEnter() {
        // Pause until the user presses Enter.
        scanner.nextLine();
    }
}
