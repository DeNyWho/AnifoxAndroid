package club.anifox.android.data.source.di

import club.anifox.android.data.local.cache.dao.anime.search.AnimeSearchDao
import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.repository.AnimeRepositoryImpl
import club.anifox.android.domain.repository.AnimeRepository
import club.anifox.android.domain.usecase.anime.GetAnimeDetailUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeGenresUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeRelatedUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeScreenshotUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeSimilarUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeVideosUseCase
import club.anifox.android.domain.usecase.anime.paging.GetAnimePagingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SourceModule {

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

    @Provides
    @Singleton
    fun provideGetAnimeUseCase(animeRepository: AnimeRepository): GetAnimeUseCase {
        return GetAnimeUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimePagingUseCase(animeRepository: AnimeRepository): GetAnimePagingUseCase {
        return GetAnimePagingUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeGenresUseCase(animeRepository: AnimeRepository): GetAnimeGenresUseCase {
        return GetAnimeGenresUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeDetailUseCase(animeRepository: AnimeRepository): GetAnimeDetailUseCase {
        return GetAnimeDetailUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeSimilarUseCase(animeRepository: AnimeRepository): GetAnimeSimilarUseCase {
        return GetAnimeSimilarUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeRelatedUseCase(animeRepository: AnimeRepository): GetAnimeRelatedUseCase {
        return GetAnimeRelatedUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeScreenshotUseCase(animeRepository: AnimeRepository): GetAnimeScreenshotUseCase {
        return GetAnimeScreenshotUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeVideosUseCase(animeRepository: AnimeRepository): GetAnimeVideosUseCase {
        return GetAnimeVideosUseCase(animeRepository)
    }
}