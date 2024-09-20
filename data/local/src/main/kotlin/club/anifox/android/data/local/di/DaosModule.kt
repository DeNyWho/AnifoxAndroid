package club.anifox.android.data.local.di

import club.anifox.android.data.local.AnifoxDatabase
import club.anifox.android.data.local.cache.dao.anime.genres.AnimeCacheGenresDao
import club.anifox.android.data.local.cache.dao.anime.search.AnimeCacheSearchDao
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

    @Provides
    fun provideAnimeCacheSearchDao(
        database: AnifoxDatabase,
    ): AnimeCacheSearchDao = database.animeCacheSearchDao()

    @Provides
    fun provideAnimeCacheGenresDao(
        database: AnifoxDatabase,
    ): AnimeCacheGenresDao = database.animeCacheGenresDao()
}
