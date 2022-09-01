package com.example.jobsearch.util.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.jobsearch.AppContext
import com.example.jobsearch.util.safeAs
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferenceDelegate<T>(
    private val key: String,
    private val defaultValue: T,
    private val prefFilename: String = "default"
) : ReadWriteProperty<Any?, T> {

    private val sharedPrefs: SharedPreferences by lazy {
        AppContext.appContext.getSharedPreferences(prefFilename, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val key = findPreferenceKey(property)
        return getPreference(key)
    }

    private fun findPreferenceKey(property: KProperty<*>): String {
        return key.ifEmpty { property.name }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getPreference(key: String): T {
        return when(defaultValue) {
            is Int -> sharedPrefs.getInt(key, defaultValue)
            is String -> sharedPrefs.getString(key, defaultValue)
            is Boolean -> sharedPrefs.getBoolean(key, defaultValue)
            is Long -> sharedPrefs.getLong(key, defaultValue)
            is Float -> sharedPrefs.getFloat(key, defaultValue)
            is Set<*> -> sharedPrefs.getStringSet(key, defaultValue.safeAs()).safeAs()
            else -> throw IllegalArgumentException("Unsupported type. $key $defaultValue")
        } as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        val key = findPreferenceKey(property)
        setPreference(key, value)
    }

    private fun setPreference(key: String, value: T) {
        sharedPrefs.edit {
            when(value) {
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Set<*> -> putStringSet(key, value.safeAs())
                else -> throw IllegalArgumentException("Unsupported type. $key $defaultValue")
            }
        }
    }

}