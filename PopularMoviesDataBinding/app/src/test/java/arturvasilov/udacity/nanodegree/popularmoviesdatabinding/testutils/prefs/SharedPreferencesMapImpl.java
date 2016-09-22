package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.testutils.prefs;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Artur Vasilov
 */
public class SharedPreferencesMapImpl implements SharedPreferences {

    private final Map<String, Object> mMap;
    private final Editor mEditor;

    public SharedPreferencesMapImpl() {
        mMap = new HashMap<>();
        mEditor = new EditorMapImpl(mMap);
    }

    @Override
    public Map<String, ?> getAll() {
        return mMap;
    }

    @Nullable
    @Override
    public String getString(String key, String defValue) {
        if (mMap.containsKey(key)) {
            return (String) mMap.get(key);
        }
        return defValue;
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        if (mMap.containsKey(key)) {
            //noinspection unchecked
            return (Set<String>) mMap.get(key);
        }
        return defValues;
    }

    @Override
    public int getInt(String key, int defValue) {
        if (mMap.containsKey(key)) {
            return (int) mMap.get(key);
        }
        return defValue;
    }

    @Override
    public long getLong(String key, long defValue) {
        if (mMap.containsKey(key)) {
            return (long) mMap.get(key);
        }
        return defValue;
    }

    @Override
    public float getFloat(String key, float defValue) {
        if (mMap.containsKey(key)) {
            return (float) mMap.get(key);
        }
        return defValue;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        if (mMap.containsKey(key)) {
            return (boolean) mMap.get(key);
        }
        return defValue;
    }

    @Override
    public boolean contains(String key) {
        return mMap.containsKey(key);
    }

    @Override
    public Editor edit() {
        return mEditor;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        throw new UnsupportedOperationException("No need for listener for testing purpose");
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        throw new UnsupportedOperationException("No need for listener for testing purpose");
    }
}
