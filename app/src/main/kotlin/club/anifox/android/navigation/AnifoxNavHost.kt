package club.anifox.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.feature.catalog.navigation.catalogScreen
import club.anifox.android.feature.catalog.navigation.navigateToCatalog
import club.anifox.android.feature.character.navigation.characterScreen
import club.anifox.android.feature.character.navigation.navigateToCharacter
import club.anifox.android.feature.characters.navigation.charactersScreen
import club.anifox.android.feature.characters.navigation.navigateToCharacters
import club.anifox.android.feature.detail.navigation.detailScreen
import club.anifox.android.feature.detail.navigation.navigateToDetail
import club.anifox.android.feature.episodes.navigation.episodesScreen
import club.anifox.android.feature.episodes.navigation.navigateToEpisodes
import club.anifox.android.feature.favourite.navigation.favouriteScreen
import club.anifox.android.feature.genres.navigation.genresScreen
import club.anifox.android.feature.genres.navigation.navigateToGenres
import club.anifox.android.feature.home.navigation.HOME_ROUTE
import club.anifox.android.feature.home.navigation.homeScreen
import club.anifox.android.feature.login.navigation.loginScreen
import club.anifox.android.feature.login.navigation.navigateToLogin
import club.anifox.android.feature.onboarding.navigation.onboardingScreen
import club.anifox.android.feature.player.navigation.navigateToPlayer
import club.anifox.android.feature.player.navigation.playerScreen
import club.anifox.android.feature.profile.navigation.profileScreen
import club.anifox.android.feature.registration.navigation.navigateToRegistration
import club.anifox.android.feature.registration.navigation.registrationScreen
import club.anifox.android.feature.schedule.navigation.scheduleScreen
import club.anifox.android.feature.screenshots.navigation.navigateToScreenshots
import club.anifox.android.feature.screenshots.navigation.screenshotsScreen
import club.anifox.android.feature.search.navigation.navigateToSearch
import club.anifox.android.feature.search.navigation.searchScreen
import club.anifox.android.feature.settings.navigation.navigateToSettings
import club.anifox.android.feature.settings.navigation.settingsScreen
import club.anifox.android.feature.translations.navigation.navigateToTranslations
import club.anifox.android.feature.translations.navigation.translationsScreen
import club.anifox.android.feature.video.navigation.navigateToVideo
import club.anifox.android.feature.video.navigation.videoScreen
import club.anifox.android.navigation.NavigationEvent.Back
import club.anifox.android.navigation.NavigationEvent.ToCatalog
import club.anifox.android.navigation.NavigationEvent.ToCharacter
import club.anifox.android.navigation.NavigationEvent.ToCharacters
import club.anifox.android.navigation.NavigationEvent.ToDetail
import club.anifox.android.navigation.NavigationEvent.ToEpisodes
import club.anifox.android.navigation.NavigationEvent.ToGenres
import club.anifox.android.navigation.NavigationEvent.ToLogin
import club.anifox.android.navigation.NavigationEvent.ToPlayer
import club.anifox.android.navigation.NavigationEvent.ToRegistration
import club.anifox.android.navigation.NavigationEvent.ToScreenshots
import club.anifox.android.navigation.NavigationEvent.ToSearch
import club.anifox.android.navigation.NavigationEvent.ToSettings
import club.anifox.android.navigation.NavigationEvent.ToTranslations
import club.anifox.android.navigation.NavigationEvent.ToVideo
import club.anifox.android.ui.AnifoxAppState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

@Stable
class NavigationManager {
    private val _navigationFlow = MutableSharedFlow<NavigationEvent>(extraBufferCapacity = 1)
    private val _backFlow = MutableSharedFlow<Back>(extraBufferCapacity = 1)

    val navigationFlow: Flow<NavigationEvent> = _navigationFlow
    val backFlow: Flow<Back> = _backFlow

    private var lastBackPressTime = 0L
    private var lastNavigationTime = 0L

    fun emit(event: NavigationEvent) {
        val currentTime = System.currentTimeMillis()

        if (event is Back) {
            if (currentTime - lastBackPressTime < 500) return
            lastBackPressTime = currentTime
            _backFlow.tryEmit(Back)
            return
        }

        if ((currentTime - lastNavigationTime < 500)) {
            return
        }

        lastNavigationTime = currentTime
        _navigationFlow.tryEmit(event)
    }
}

sealed interface NavigationEvent {
    data class ToDetail(val url: String) : NavigationEvent
    data class ToCharacter(val characterId: String) : NavigationEvent
    data class ToTranslations(val url: String) : NavigationEvent
    data class ToEpisodes(val url: String, val translationId: Int) : NavigationEvent
    data class ToPlayer(val url: String, val kodik: Boolean?) : NavigationEvent
    data class ToScreenshots(val url: String, val title: String) : NavigationEvent
    data class ToVideo(val url: String, val title: String?) : NavigationEvent
    data class ToCatalog(val params: CatalogFilterParams) : NavigationEvent
    data class ToGenres(val genre: AnimeGenre) : NavigationEvent
    data class ToCharacters(val url: String, val title: String) : NavigationEvent

    data object ToSearch : NavigationEvent
    data object ToSettings : NavigationEvent
    data object ToLogin : NavigationEvent
    data object ToRegistration : NavigationEvent
    data object Back : NavigationEvent
}

@Composable
fun AnifoxNavHost(
    appState: AnifoxAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
    isFirstLaunch: Boolean,
    navigationManager: NavigationManager = remember { NavigationManager() }
) {
    val navController = appState.navController

    LaunchedEffect(navigationManager, navController) {
        navigationManager.navigationFlow.collect { event ->
            when (event) {
                is ToDetail -> navController.navigateToDetail(event.url)
                is ToCharacter -> navController.navigateToCharacter(event.characterId)
                is ToTranslations -> navController.navigateToTranslations(event.url)
                is ToEpisodes -> navController.navigateToEpisodes(event.url, event.translationId)
                is ToPlayer -> navController.navigateToPlayer(event.url, event.kodik)
                is ToScreenshots -> navController.navigateToScreenshots(event.url, event.title)
                is ToVideo -> navController.navigateToVideo(event.url, event.title)
                is ToCatalog -> navController.navigateToCatalog(event.params)
                is ToGenres -> navController.navigateToGenres(event.genre)
                is ToCharacters -> navController.navigateToCharacters(event.url, event.title)
                ToSearch -> navController.navigateToSearch()
                ToSettings -> navController.navigateToSettings()
                ToLogin -> navController.navigateToLogin()
                ToRegistration -> navController.navigateToRegistration()
                Back -> {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    LaunchedEffect(navigationManager, navController) {
        navigationManager.backFlow.collect {
            if (navController.previousBackStackEntry != null) {
                navController.popBackStack()
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (isFirstLaunch) startDestination else startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            onAnimeClick = { animeId -> navigationManager.emit(ToDetail(animeId)) },
            onSearchClick = { navigationManager.emit(ToSearch) },
            onGenresClick = { genre -> navigationManager.emit(ToGenres(genre)) },
            onCatalogClick = { params -> navigationManager.emit(ToCatalog(params)) },
            onSettingsClick = { navigationManager.emit(ToSettings) }
        )
        detailScreen(
            onBackPressed = { navigationManager.emit(Back) },
            onWatchClick = { animeId -> navigationManager.emit(ToTranslations(animeId)) },
            onAnimeClick = { animeId -> navigationManager.emit(ToDetail(animeId)) },
            onMoreScreenshotClick = { url, title -> navigationManager.emit(ToScreenshots(url, title)) },
            onMoreVideoClick = { url, title -> navigationManager.emit(ToVideo(url, title)) },
//            onCatalogClick = { params -> navigationManager.emit(ToCatalog(params)) },
            onCharacterClick = { characterId -> navigationManager.emit(ToCharacter(characterId)) },
            onMoreCharactersClick = { url, title -> navigationManager.emit(ToCharacters(url, title)) }
        )
        characterScreen(
            onBackPressed = { navigationManager.emit(Back) },
            onAnimeClick = { animeId -> navigationManager.emit(ToDetail(animeId)) }
        )
        charactersScreen(
            onBackPressed = { navigationManager.emit(Back) },
            onCharacterClick = { characterId -> navigationManager.emit(ToCharacter(characterId)) }
        )
        translationsScreen(
            onBackPressed = { navigationManager.emit(Back) },
            onTranslationClick = { url, translationId -> navigationManager.emit(ToEpisodes(url, translationId)) },
            onPlayerClick = { url, kodik -> navigationManager.emit(ToPlayer(url, kodik)) },
        )
        episodesScreen(
            onBackPressed = { navigationManager.emit(Back) },
            onEpisodeClick = { url, kodik -> navigationManager.emit(ToPlayer(url, kodik)) }
        )
        playerScreen()
        searchScreen(
            onBackPressed = { navigationManager.emit(Back) },
            onAnimeClick = { animeId -> navigationManager.emit(ToDetail(animeId)) }
        )
        screenshotsScreen(
            onBackPressed = { navigationManager.emit(Back) }
        )
        videoScreen(
            onBackPressed = { navigationManager.emit(Back) }
        )
        scheduleScreen(
            onAnimeClick = { animeId -> navigationManager.emit(ToDetail(animeId)) }
        )
        profileScreen(
            onLoginClick = { navigationManager.emit(ToLogin) },
            onRegistrationClick = { navigationManager.emit(ToRegistration) }
        )
        favouriteScreen(
            onAnimeClick = { animeId -> navigationManager.emit(ToDetail(animeId)) }
        )
        loginScreen()
        registrationScreen()
        genresScreen(
            onAnimeClick = { animeId -> navigationManager.emit(ToDetail(animeId)) },
            onBackPressed = { navigationManager.emit(Back) }
        )
        catalogScreen(
            onAnimeClick = { animeId -> navigationManager.emit(ToDetail(animeId)) },
            onSearchClick = { navigationManager.emit(ToSearch) },
            onBackPressed = { navigationManager.emit(Back) }
        )
        settingsScreen(
            onBackPressed = { navigationManager.emit(Back) }
        )
        onboardingScreen(
            onLoginClick = { navigationManager.emit(ToLogin) },
            onRegistrationClick = { navigationManager.emit(ToRegistration) }
        )
    }
}
