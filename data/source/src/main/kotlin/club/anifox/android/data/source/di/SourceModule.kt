package club.anifox.android.data.source.di

import club.anifox.android.data.local.cache.dao.anime.search.AnimeSearchDao
import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.repository.AnimeRepositoryImpl
import club.anifox.android.domain.repository.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SourceModule {

    @Provides
    @Singleton
    fun provideAnimeRepository(
        animeService: AnimeService,
        animeDao: AnimeDao,
        animeSearchDao: AnimeSearchDao,
    ): AnimeRepository {
        return AnimeRepositoryImpl(
            animeService = animeService,
            animeDao = animeDao,
            animeSearchDao = animeSearchDao,
        )
    }
}