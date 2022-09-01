package com.example.jobsearch.data.prefs

import com.example.jobsearch.BuildConfig
import com.example.jobsearch.util.prefs.PreferenceDelegate

object AppPreferences {

    private const val PREF_NAME = "${BuildConfig.APPLICATION_ID}_preferences"

    var isFirstLaunch by pref("is_first_launch", true)

    private fun <T> pref(key: String, defaultValue: T): PreferenceDelegate<T> {
        return PreferenceDelegate(
            key,
            defaultValue,
            PREF_NAME
        )
    }

}