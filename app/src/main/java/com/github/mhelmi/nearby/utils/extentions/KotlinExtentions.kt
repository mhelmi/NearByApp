package com.github.mhelmi.nearby.utils.extentions

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.github.mhelmi.nearby.App
import com.github.mhelmi.nearby.R
import com.github.mhelmi.nearby.di.AppComponent
import com.github.mhelmi.nearby.utils.glide.GlideApp

val Context.app: App
    get() = applicationContext as App

/**
 * enable dagger AppComponent for any Context
 */
val Context.appComponent: AppComponent
    get() = app.appComponent

/**
 * tag for any class
 */
inline val <reified T> T.TAG: String get() = T::class.java.simpleName

/**
 * log types enabled to any object
 */
inline fun <reified T> T.logv(message: String) = Log.v(TAG, message)

inline fun <reified T> T.loge(message: String) = Log.e(TAG, message)
inline fun <reified T> T.logd(message: String) = Log.d(TAG, message)
inline fun <reified T> T.logi(message: String) = Log.i(TAG, message)
inline fun <reified T> T.logw(message: String) = Log.w(TAG, message)
inline fun <reified T> T.logwtf(message: String) = Log.wtf(TAG, message)

/**
 * load photo into ImageView from remote server
 * @param url photo remote url
 */
fun ImageView.load(url: String?) {
    GlideApp.with(context)
        .load(url)
        .placeholder(R.drawable.ic_photo_placeholder)
        .into(this)
}

/**
 * check view visibility
 */
fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

/**
 * show or hide view
 * @param visible if true will show else will hide
 */
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * Open Location Settings from any Context
 */
fun Context.openLocationSettings() {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    startActivity(intent)
}