package com.github.mhelmi.nearby.utils

import android.content.SharedPreferences
import javax.inject.Inject

const val LOCATION_UPDATE_MODE = "location_update_mode"

class SessionManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) {

    var locationUpdateMode: Int
        get() = sharedPreferences.getInt(LOCATION_UPDATE_MODE, LocationUpdateMode.RUNTIME)
        set(value) = editor.putInt(LOCATION_UPDATE_MODE, value).apply()

}

object LocationUpdateMode {
    const val RUNTIME = 1
    const val SINGLE_UPDATE = 2
}