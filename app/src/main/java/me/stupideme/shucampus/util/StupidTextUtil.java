package me.stupideme.shucampus.util;

/**
 * Created by StupidL on 2016/12/9.
 */

public class StupidTextUtil {

    public static boolean isDigitalInRange(int l, int h, CharSequence sequence) {
        if (sequence.length() <= 0)
            return false;
        int val = Integer.parseInt(sequence.toString());
        return !(val < l || val > h);
    }
}
