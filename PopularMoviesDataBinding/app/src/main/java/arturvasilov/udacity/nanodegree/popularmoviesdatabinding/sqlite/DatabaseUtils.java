package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.sqlite;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Artur Vasilov
 */
public class DatabaseUtils {

    public static String safeStringFromCursor(@NonNull Cursor cursor, @NonNull String column) {
        try {
            return cursor.getString(cursor.getColumnIndex(column));
        } catch (Exception e) {
            return "";
        }
    }

    public static int safeIntFromCursor(@NonNull Cursor cursor, @NonNull String column) {
        try {
            return cursor.getInt(cursor.getColumnIndex(column));
        } catch (Exception e) {
            return 0;
        }
    }

    public static void safeCloseCursor(@Nullable Cursor cursor) {
        try {
            if (cursor == null || cursor.isClosed()) {
                return;
            }
            cursor.close();
        } catch (Exception ignored) {
        }
    }

    public static boolean isEmptyCursor(@Nullable Cursor cursor) {
        return cursor == null || cursor.isClosed() || !cursor.moveToFirst();
    }
}
