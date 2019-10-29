package com.github.mhelmi.nearby.di

import android.content.Context
import androidx.room.Room
import com.github.mhelmi.nearby.features.nearbyplaces.data.local.VenuesDao
import com.github.mhelmi.nearby.general.db.NearbyRoomDatabase
import com.github.mhelmi.nearby.utils.KEY_DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    internal fun providesRoomDatabase(context: Context): NearbyRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext, NearbyRoomDatabase::class.java, KEY_DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    internal fun provideVenuesDao(nearbyRoomDatabase: NearbyRoomDatabase) =
        nearbyRoomDatabase.venueDao()

}