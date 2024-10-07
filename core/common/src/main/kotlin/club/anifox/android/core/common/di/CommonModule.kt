package club.anifox.android.core.common.di

import android.content.Context
import club.anifox.android.core.common.util.deeplink.DeepLink
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CommonModule {

    @Provides
    @Singleton
    fun provideDeepLink(
        @ApplicationContext context: Context
    ): DeepLink {
        return DeepLink(context)
    }

}