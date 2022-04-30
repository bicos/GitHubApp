package com.ravy.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.ravy.data.pref.AppPreference.Companion.PREF_KEY_USER_ID
import com.ravy.data.pref.AppPreference.Companion.PREF_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface AppPreference {
    val prefs: SharedPreferences
    var userId: String

    companion object {
        const val PREF_NAME = "github_app"
        const val PREF_KEY_USER_ID = "user_id"
    }
}

class AppPreferenceImpl @Inject constructor(
    @ApplicationContext context: Context,
) : AppPreference {

    override val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    override var userId: String
        get() = prefs.getString(PREF_KEY_USER_ID, "") ?: ""
        set(value) {
            prefs.edit().putString(PREF_KEY_USER_ID, value).apply()
        }

}