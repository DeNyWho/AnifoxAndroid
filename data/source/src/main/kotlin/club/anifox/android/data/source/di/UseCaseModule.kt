package club.anifox.android.data.source.di

import club.anifox.android.domain.repository.anime.AnimeFavouriteRepository
import club.anifox.android.domain.repository.anime.AnimeLocalRepository
import club.anifox.android.domain.repository.anime.AnimeRepository
import club.anifox.android.domain.repository.character.CharacterRepository
import club.anifox.android.domain.repository.user.UserRepository
import club.anifox.android.domain.repository.user.UserSecurityRepository
import club.anifox.android.domain.usecase.anime.GetAnimeDetailUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeGenresUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeRelatedUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeScreenshotUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeSimilarUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeStudiosUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeTranslationsCountUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeTranslationsUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeVideosUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeYearsUseCase
import club.anifox.android.domain.usecase.anime.characters.GetAnimeCharactersUseCase
import club.anifox.android.domain.usecase.anime.favourite.CheckAnimeFavouriteUseCase
import club.anifox.android.domain.usecase.anime.favourite.GetFavouriteAnimeUseCase
import club.anifox.android.domain.usecase.anime.favourite.UpdateAnimeUseCase
import club.anifox.android.domain.usecase.anime.local.CheckAnimeLocalUseCase
import club.anifox.android.domain.usecase.anime.local.InsertAnimeLocalUseCase
import club.anifox.android.domain.usecase.anime.local.ObserveAnimeExistsUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.catalog.AnimeCatalogPagingUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.characters.AnimeCharactersPagingUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.episodes.AnimeEpisodesPagingUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.genres.AnimeGenresPagingUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.schedule.AnimeSchedulePagingUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.search.AnimeSearchPagingUseCase
import club.anifox.android.domain.usecase.anime.search.AddAnimeSearchHistoryUseCase
import club.anifox.android.domain.usecase.anime.search.DeleteAnimeSearchHistoryUseCase
import club.anifox.android.domain.usecase.anime.search.GetAnimeSearchHistoryUseCase
import club.anifox.android.domain.usecase.character.GetCharacterFullUseCase
import club.anifox.android.domain.usecase.settings.PlayerOrientationSettingsUseCase
import club.anifox.android.domain.usecase.settings.ThemeSettingsUseCase
import club.anifox.android.domain.usecase.user.UserFirstLaunchUseCase
import club.anifox.android.domain.usecase.user.UserSettingsUseCase
import club.anifox.android.domain.usecase.user.UserTokensUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UseCaseModule {

    @Provides
    @Singleton
    fun provideUserFirstLaunchUseCase(userRepository: UserRepository): UserFirstLaunchUseCase {
        return UserFirstLaunchUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideUserSettingsUseCase(userRepository: UserRepository): UserSettingsUseCase {
        return UserSettingsUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideThemeSettingsUseCase(userRepository: UserRepository): ThemeSettingsUseCase {
        return ThemeSettingsUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun providePlayerOrientationSettingsUseCase(userRepository: UserRepository): PlayerOrientationSettingsUseCase {
        return PlayerOrientationSettingsUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideUserTokensUseCase(userSecurityRepository: UserSecurityRepository): UserTokensUseCase {
        return UserTokensUseCase(userSecurityRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeUseCase(animeRepository: AnimeRepository): GetAnimeUseCase {
        return GetAnimeUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideInsertAnimeLocalUseCase(animeLocalRepository: AnimeLocalRepository): InsertAnimeLocalUseCase {
        return InsertAnimeLocalUseCase(animeLocalRepository)
    }

    @Provides
    @Singleton
    fun provideCheckAnimeLocalUseCase(animeLocalRepository: AnimeLocalRepository): CheckAnimeLocalUseCase {
        return CheckAnimeLocalUseCase(animeLocalRepository)
    }

    @Provides
    @Singleton
    fun provideObserveAnimeExistsUseCase(animeLocalRepository: AnimeLocalRepository): ObserveAnimeExistsUseCase {
        return ObserveAnimeExistsUseCase(animeLocalRepository)
    }

    @Provides
    @Singleton
    fun provideGetFavouriteAnimeUseCase(animeFavouriteRepository: AnimeFavouriteRepository): GetFavouriteAnimeUseCase {
        return GetFavouriteAnimeUseCase(animeFavouriteRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateAnimeUseCase(animeFavouriteRepository: AnimeFavouriteRepository): UpdateAnimeUseCase {
        return UpdateAnimeUseCase(animeFavouriteRepository)
    }

    @Provides
    @Singleton
    fun provideCheckAnimeFavouriteUseCase(animeFavouriteRepository: AnimeFavouriteRepository): CheckAnimeFavouriteUseCase {
        return CheckAnimeFavouriteUseCase(animeFavouriteRepository)
    }

    @Provides
    @Singleton
    fun provideAnimeSearchPagingUseCase(animeRepository: AnimeRepository): AnimeSearchPagingUseCase {
        return AnimeSearchPagingUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideAddAnimeSearchHistoryUseCase(animeRepository: AnimeRepository): AddAnimeSearchHistoryUseCase {
        return AddAnimeSearchHistoryUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteAnimeSearchHistoryUseCase(animeRepository: AnimeRepository): DeleteAnimeSearchHistoryUseCase {
        return DeleteAnimeSearchHistoryUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeSearchHistoryUseCase(animeRepository: AnimeRepository): GetAnimeSearchHistoryUseCase {
        return GetAnimeSearchHistoryUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideAnimeCatalogPagingUseCase(animeRepository: AnimeRepository): AnimeCatalogPagingUseCase {
        return AnimeCatalogPagingUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideAnimeGenresPagingUseCase(animeRepository: AnimeRepository): AnimeGenresPagingUseCase {
        return AnimeGenresPagingUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideAnimeSchedulePagingUseCase(animeRepository: AnimeRepository): AnimeSchedulePagingUseCase {
        return AnimeSchedulePagingUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideAnimeEpisodesPagingUseCase(animeRepository: AnimeRepository): AnimeEpisodesPagingUseCase {
        return AnimeEpisodesPagingUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideAnimeCharactersPagingUseCase(animeRepository: AnimeRepository): AnimeCharactersPagingUseCase {
        return AnimeCharactersPagingUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeGenresUseCase(animeRepository: AnimeRepository): GetAnimeGenresUseCase {
        return GetAnimeGenresUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeYearsUseCase(animeRepository: AnimeRepository): GetAnimeYearsUseCase {
        return GetAnimeYearsUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeStudiosUseCase(animeRepository: AnimeRepository): GetAnimeStudiosUseCase {
        return GetAnimeStudiosUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeTranslationsUseCase(animeRepository: AnimeRepository): GetAnimeTranslationsUseCase {
        return GetAnimeTranslationsUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAnimeTranslationsCountUseCase(animeRepository: AnimeRepository): GetAnimeTranslationsCountUseCase {
        return GetAnimeTranslationsCountUseCase(animeRepository)
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
    fun provideGetAnimeCharactersUseCase(animeRepository: AnimeRepository): GetAnimeCharactersUseCase {
        return GetAnimeCharactersUseCase(animeRepository)
    }

    @Provides
    @Singleton
    fun provideGetCharacterFullUseCase(characterRepository: CharacterRepository): GetCharacterFullUseCase {
        return GetCharacterFullUseCase(characterRepository)
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