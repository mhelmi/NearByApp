package com.github.mhelmi.nearby

import androidx.multidex.MultiDexApplication
import com.github.mhelmi.nearby.di.AppComponent
import com.github.mhelmi.nearby.di.ContextModule
import com.github.mhelmi.nearby.di.DaggerAppComponent

class App : MultiDexApplication() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory()
            .create(ContextModule(this))
    }

}