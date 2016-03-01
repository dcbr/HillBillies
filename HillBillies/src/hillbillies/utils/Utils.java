package hillbillies.utils;

/**
 * Created by Bram on 1-3-2016.
 */
public final class Utils {

    public static int[] addVectors(int[] v1, int[] v2){
        int[] result = new int[v1.length];
        for(int i=0;i<v1.length;i++){
            result[i] = v1[i]+v2[i];
        }
        return result;
    }

    public static double[] addVectors(double[] v1, double[] v2){
        double[] result = new double[v1.length];
        for(int i=0;i<v1.length;i++){
            result[i] = v1[i]+v2[i];
        }
        return result;
    }
}
