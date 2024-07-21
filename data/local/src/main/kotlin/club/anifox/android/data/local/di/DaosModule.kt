package club.anifox.android.data.local.di

import club.anifox.android.data.local.AnifoxDatabase
import club.anifox.android.data.local.dao.anime.AnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun provideAnimeDao(
        database: AnifoxDatabase,
    ): AnimeDao = database.animeDao()
}
