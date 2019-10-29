package com.github.mhelmi.nearby.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity
import com.github.mhelmi.nearby.utils.extentions.showMainActivity
import com.github.mhelmi.nearby.general.DeleteAllDatabaseTablesAsyncTask
import com.github.mhelmi.nearby.general.db.NearbyRoomDatabase
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor,
    private val nearbyRoomDatabase: NearbyRoomDatabase
) {

    private val LOCATION_UPDATE_MODE = "location_update_mode"
//    private val IS_LOGIN = "is_login"
//    private val USER_ACCESS_CODE = "user_access_code"
//    private val USER_ACCESS_TOKEN = "user_access_token"


    var locationUpdateMode: Int
        get() = sharedPreferences.getInt(LOCATION_UPDATE_MODE, LocationUpdateMode.RUNTIME)
        set(value) = editor.putInt(LOCATION_UPDATE_MODE, value).apply()

//    var isLogin: Boolean
//        get() = sharedPreferences.getBoolean(IS_LOGIN, false)
//        set(value) = editor.putBoolean(IS_LOGIN, value).apply()
//
//    var userAccessCode: String?
//        get() = sharedPreferences.getString(USER_ACCESS_CODE, null)
//        set(value) = editor.putString(USER_ACCESS_CODE, value).apply()
//
//    var userAccessToken: String?
//        get() = sharedPreferences.getString(USER_ACCESS_TOKEN, null)
//        set(value) = editor.putString(USER_ACCESS_TOKEN, value).apply()

//    fun logout(activity: FragmentActivity) {
//        DeleteAllDatabaseTablesAsyncTask(nearbyRoomDatabase).execute()
//        editor.clear().apply()
//        context.showMainActivity()
//        activity.finishAffinity()
//    }

}

object LocationUpdateMode {
    const val RUNTIME = 1
    const val SINGLE_UPDATE = 2
}