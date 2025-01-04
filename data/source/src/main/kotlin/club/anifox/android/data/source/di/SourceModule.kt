package club.anifox.android.data.source.di

import club.anifox.android.data.datastore.source.UserDataSource
import club.anifox.android.data.datastore.source.UserSecurityDataSource
import club.anifox.android.data.local.cache.dao.anime.catalog.AnimeCacheCatalogDao
import club.anifox.android.data.local.cache.dao.anime.characters.AnimeCacheCharactersDao
import club.anifox.android.data.local.cache.dao.anime.episodes.AnimeCacheEpisodesDao
import club.anifox.android.data.local.cache.dao.anime.genres.AnimeCacheGenresDao
import club.anifox.android.data.local.cache.dao.anime.schedule.AnimeCacheScheduleDao
import club.anifox.android.data.local.cache.dao.anime.search.AnimeCacheSearchDao
import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.local.dao.anime.favourite.AnimeFavouriteDao
import club.anifox.android.data.local.dao.anime.search.AnimeSearchHistoryDao
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.network.service.CharacterService
import club.anifox.android.data.source.repository.anime.AnimeRepositoryImpl
import club.anifox.android.data.source.repository.anime.favourite.AnimeFavouriteRepositoryImpl
import club.anifox.android.data.source.repository.character.CharacterRepositoryImpl
import club.anifox.android.data.source.repository.user.UserRepositoryImpl
import club.anifox.android.data.source.repository.user.UserSecurityRepositoryImpl
import club.anifox.android.domain.repository.anime.AnimeFavouriteRepository
import club.anifox.android.domain.repository.anime.AnimeRepository
import club.anifox.android.domain.repository.character.CharacterRepository
import club.anifox.android.domain.repository.user.UserRepository
import club.anifox.android.domain.repository.user.UserSecurityRepository
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
        animeSearchHistoryDao: AnimeSearchHistoryDao,
        animeCacheSearchDao: AnimeCacheSearchDao,
        animeCacheGenresDao: AnimeCacheGenresDao,
        animeCacheCatalogDao: AnimeCacheCatalogDao,
        animeCacheEpisodesDao: AnimeCacheEpisodesDao,
        animeCacheScheduleDao: AnimeCacheScheduleDao,
        animeCacheCharactersDao: AnimeCacheCharactersDao,
    ): AnimeRepository {
        return AnimeRepositoryImpl(
            animeService = animeService,
            animeDao = animeDao,
            animeSearchHistoryDao = animeSearchHistoryDao,
            animeCacheSearchDao = animeCacheSearchDao,
            animeCacheGenresDao = animeCacheGenresDao,
            animeCacheCatalogDao = animeCacheCatalogDao,
            animeCacheEpisodesDao = animeCacheEpisodesDao,
            animeCacheScheduleDao = animeCacheScheduleDao,
            animeCacheCharactersDao = animeCacheCharactersDao,
        )
    }

    @Provides
    @Singleton
    fun provideFavouriteRepository(
        favouriteDao: AnimeFavouriteDao,
    ): AnimeFavouriteRepository {
        return AnimeFavouriteRepositoryImpl(
            favouriteDao = favouriteDao,
        )
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(
        characterService: CharacterService,
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            characterService = characterService,
        )
    }

    @Provides
    @Singleton
    fun provideUserSecurityRepository(
        userSecurityDataSource: UserSecurityDataSource,
    ): UserSecurityRepository {
        return UserSecurityRepositoryImpl(userSecurityDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userDataSource: UserDataSource,
    ): UserRepository {
        return UserRepositoryImpl(userDataSource)
    }
}