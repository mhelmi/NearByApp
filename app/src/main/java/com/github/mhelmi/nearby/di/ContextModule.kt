package com.github.mhelmi.nearby.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule constructor(private val context: Context) {
    @Provides
    fun provideContext() = context
}