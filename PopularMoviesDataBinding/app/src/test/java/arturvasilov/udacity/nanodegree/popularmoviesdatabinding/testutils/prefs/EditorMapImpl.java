package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.testutils.prefs;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Map;
import java.util.Set;

/**
 * @author Artur Vasilov
 */
class EditorMapImpl implements SharedPreferences.Editor {

    private final Map<String, Object> mMap;

    public EditorMapImpl(@NonNull Map<String, Object> map) {
        mMap = map;
    }

    @Override
    public SharedPreferences.Editor putString(String key, String value) {
        mMap.put(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
        mMap.put(key, values);
        return this;
    }

    @Override
    public SharedPreferences.Editor putInt(String key, int value) {
        mMap.put(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putLong(String key, long value) {
        mMap.put(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putFloat(String key, float value) {
        mMap.put(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        mMap.put(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor remove(String key) {
        mMap.remove(key);
        return this;
    }

    @Override
    public SharedPreferences.Editor clear() {
        mMap.clear();
        return this;
    }

    @Override
    public boolean commit() {
        return true;
    }

    @Override
    public void apply() {
        // Do nothing
    }
}
