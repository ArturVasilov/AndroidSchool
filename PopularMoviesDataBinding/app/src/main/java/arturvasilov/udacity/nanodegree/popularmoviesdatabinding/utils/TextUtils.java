package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.utils;

import android.support.annotation.Nullable;

/**
 * @author Artur Vasilov
 *
 * Simple replacement for android.text.TextUtils to make tests easier
 */
public final class TextUtils {

    private TextUtils() {
    }

    public static boolean equals(@Nullable CharSequence first, @Nullable CharSequence second) {
        if (first == second) {
            return true;
        }

        int length;
        if (first != null && second != null && (length = first.length()) == second.length()) {
            if (first instanceof String && second instanceof String) {
                return first.equals(second);
            } else {
                for (int i = 0; i < length; i++) {
                    if (first.charAt(i) != second.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

}
