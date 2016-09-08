package ru.gdgkazan.simpleweather.data.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import org.sqlite.database.sqlite.SQLiteDatabase;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;
import ru.gdgkazan.simpleweather.network.model.NetworkRequest;
import ru.gdgkazan.simpleweather.network.model.Request;
import ru.gdgkazan.simpleweather.network.model.RequestStatus;

/**
 * @author Artur Vasilov
 */
public class RequestTable extends BaseTable<Request> {

    public static final Table<Request> TABLE = new RequestTable();

    public static final String REQUEST = "request";
    public static final String STATUS = "status";
    public static final String ERROR = "error";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .textColumn(REQUEST)
                .textColumn(STATUS)
                .textColumn(ERROR)
                .primaryKey(REQUEST)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull Request request) {
        ContentValues values = new ContentValues();
        values.put(REQUEST, request.getRequest());
        values.put(STATUS, request.getStatus().name());
        values.put(ERROR, request.getError());
        return values;
    }

    @NonNull
    @Override
    public Request fromCursor(@NonNull Cursor cursor) {
        @NetworkRequest String request = cursor.getString(cursor.getColumnIndex(REQUEST));
        RequestStatus status = RequestStatus.valueOf(cursor.getString(cursor.getColumnIndex(STATUS)));
        String error = cursor.getString(cursor.getColumnIndex(ERROR));
        return new Request(request, status, error);
    }
}
