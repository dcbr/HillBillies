package hillbillies.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class
 * @author Kenneth & Bram
 * @version 1.0
 */
public final class Utils {

    /**
     * Returns a random integer between min and max, inclusive.
     *
     * @param 	min
     * 			Minimum value
     * @param 	max
     * 			Maximum value.
     * @throws  IllegalArgumentException
     *          min is greater than max
     * 			| (max < min)
     * @return  A random integer between min and max (both included).
     *          | min <= result <= max
     */
    public static int randInt(int min, int max) throws IllegalArgumentException {
        if (max < min)
            throw new IllegalArgumentException();
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
