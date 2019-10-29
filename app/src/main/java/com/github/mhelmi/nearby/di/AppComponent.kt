package com.github.mhelmi.nearby.di

import android.content.Context
import com.github.mhelmi.nearby.features.nearbyplaces.ui.activities.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class, RoomModule::class, PrefsModule::class,
        ViewModelModule::class, WebServiceModule::class
    ]
)
interface AppComponent {
    fun appContext(): Context

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(contextModule: ContextModule): AppComponent
    }
}