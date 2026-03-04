/*
 * Abdul Rahman Fornah
 * aforna1@umbc.edu
 */
package io;

import exceptions.InvalidMenuChoiceException;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getIntInput() throws InvalidMenuChoiceException {
        // Read a whole line to avoid Scanner nextInt/nextLine mixing issues.
        String line = scanner.nextLine();
        if (line == null || line.trim().isEmpty()) {
            throw new InvalidMenuChoiceException("Please enter a number.");
        }
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            throw new InvalidMenuChoiceException("Please enter a number.");
        }
    }


    public static @NotNull String getStringInput() throws NullPointerException {
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

    public static void waitForEnter() {
        // Pause until the user presses Enter.
        scanner.nextLine();
    }
}
