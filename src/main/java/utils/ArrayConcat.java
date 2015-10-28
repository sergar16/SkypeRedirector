package utils;

import java.util.Arrays;

/**
 * Created by Serhii on 10/26/2015.
 */
public class ArrayConcat {
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
