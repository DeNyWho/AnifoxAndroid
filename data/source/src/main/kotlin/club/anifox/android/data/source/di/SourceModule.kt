package club.anifox.android.data.source.di

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
object SourceModule {
    @Provides
    @Singleton
    fun provideAnimeRepository(
        animeService: AnimeService
    ): AnimeRepository {
        return AnimeRepositoryImpl(animeService)
    }
}