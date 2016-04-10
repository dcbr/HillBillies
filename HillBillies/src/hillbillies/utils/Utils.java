package hillbillies.utils;

import hillbillies.model.IWorld;
import hillbillies.model.World;

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
    
    /**
     * Returns a random double between min inclusive and max exclusive.
     *
     * @param 	min
     * 			Minimum value
     * @param 	max
     * 			Maximum value.
     * @throws  IllegalArgumentException
     *          min is greater than max
     * 			| (max < min)
     * @return  A random double between min included and max excluded).
     *          | min <= result < max
     */
    public static double randDouble(double min, double max) throws IllegalArgumentException {
        if (max < min)
            throw new IllegalArgumentException();
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    
    public static final class ArrayConvert{
        public static double[] intToDouble(int[] array){
            double[] result = new double[array.length];
            for(int i=0;i<result.length;i++)
                result[i] = (double)array[i];
            return result;
        }
        public static int[] doubleToInt(double[] array){
            int[] result = new int[array.length];
            for(int i=0;i<result.length;i++)
                result[i] = (int)array[i];
            return result;
        }
        public static double[] longToDouble(long[] array){
            double[] result = new double[array.length];
            for(int i=0;i<result.length;i++)
                result[i] = (double)array[i];
            return result;
        }
        public static long[] doubleToLong(double[] array){
            long[] result = new long[array.length];
            for(int i=0;i<result.length;i++)
                result[i] = (long)array[i];
            return result;
        }
    }
}
