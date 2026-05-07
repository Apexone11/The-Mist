package util;

/**
 * Utility class for generating random numbers.
 */
public class RandomUtil {
    /**
     * Generates a random double between 0.0 and 1.0.
     * @return a random double
     */
    public static double randomDouble() {
        return Math.random();
    }

    /**
     * Generates a random integer within a specified range.
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a random integer between min and max
     */
    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
