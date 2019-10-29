package com.github.mhelmi.nearby.di

import androidx.lifecycle.ViewModel
import com.github.mhelmi.nearby.features.nearbyplaces.data.VenuesRepository
import com.github.mhelmi.nearby.features.nearbyplaces.viewmodels.VenuesViewModel
import com.github.mhelmi.nearby.general.ViewModelFactory
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
class ViewModelModule {
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Provides
    internal fun provideViewModelFactory(
        providerMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelFactory {
        return ViewModelFactory(providerMap)
    }

    @Provides
    @IntoMap
    @ViewModelKey(VenuesViewModel::class)
    internal fun provideLoginViewModel(
        repository: VenuesRepository
    ): ViewModel {
        return VenuesViewModel(repository)
    }

}