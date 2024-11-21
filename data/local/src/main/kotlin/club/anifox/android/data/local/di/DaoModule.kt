package club.anifox.android.data.local.di

import club.anifox.android.data.local.AnifoxDatabase
import club.anifox.android.data.local.cache.dao.anime.catalog.AnimeCacheCatalogDao
import club.anifox.android.data.local.cache.dao.anime.episodes.AnimeCacheEpisodesDao
import club.anifox.android.data.local.cache.dao.anime.genres.AnimeCacheGenresDao
import club.anifox.android.data.local.cache.dao.anime.schedule.AnimeCacheScheduleDao
import club.anifox.android.data.local.cache.dao.anime.search.AnimeCacheSearchDao
import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.local.dao.anime.favourite.AnimeFavouriteDao
import club.anifox.android.data.local.dao.anime.search.AnimeSearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun provideAnimeDao(
        database: AnifoxDatabase,
    ): AnimeDao = database.animeDao()

    @Provides
    fun provideAnimeFavouriteStatusDao(
        database: AnifoxDatabase,
    ): AnimeFavouriteDao = database.animeFavouriteStatusDao()

    @Provides
    fun provideAnimeSearchHistoryDao(
        database: AnifoxDatabase,
    ): AnimeSearchHistoryDao = database.animeSearchHistoryDao()

    @Provides
    fun provideAnimeCacheCatalogDao(
        database: AnifoxDatabase,
    ): AnimeCacheCatalogDao = database.animeCacheCatalogDao()

    @Provides
    fun provideAnimeCacheScheduleDao(
        database: AnifoxDatabase
    ): AnimeCacheScheduleDao = database.animeCacheScheduleDao()

    @Provides
    fun provideAnimeCacheSearchDao(
        database: AnifoxDatabase,
    ): AnimeCacheSearchDao = database.animeCacheSearchDao()

    @Provides
    fun provideAnimeCacheGenresDao(
        database: AnifoxDatabase,
    ): AnimeCacheGenresDao = database.animeCacheGenresDao()

    @Provides
    fun provideAnimeCacheEpisodesDao(
        database: AnifoxDatabase,
    ): AnimeCacheEpisodesDao = database.animeCacheEpisodesDao()
}
