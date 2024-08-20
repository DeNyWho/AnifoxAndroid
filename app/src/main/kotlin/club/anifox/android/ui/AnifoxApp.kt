package club.anifox.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import club.anifox.android.R
import club.anifox.android.core.uikit.component.AnifoxBackground
import club.anifox.android.core.uikit.component.icon.AnifoxIcon
import club.anifox.android.core.uikit.component.navigation.AnifoxNavigationBar
import club.anifox.android.core.uikit.component.navigation.AnifoxNavigationBarItem
import club.anifox.android.feature.favourite.navigation.FAVOURITE_ROUTE
import club.anifox.android.feature.home.navigation.HOME_ROUTE
import club.anifox.android.feature.profile.navigation.PROFILE_ROUTE
import club.anifox.android.navigation.AnifoxNavHost
import club.anifox.android.navigation.TopLevelDestination

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnifoxApp(appState: AnifoxAppState) {
    val screensWithNavBar = listOf(
        HOME_ROUTE,
        FAVOURITE_ROUTE,
        PROFILE_ROUTE,
    )

    AnifoxBackground {
        val snackbarHostState = remember { SnackbarHostState() }
        val currentDestination = appState.currentDestination

        val isOffline by appState.isOffline.collectAsStateWithLifecycle()

        val notConnectedMessage = stringResource(R.string.not_connected)
        LaunchedEffect(isOffline) {
            if (isOffline) {
                snackbarHostState.showSnackbar(
                    message = notConnectedMessage,
                    duration = Indefinite,
                )
            }
        }

        val showNavBar = currentDestination?.route in screensWithNavBar

        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if(showNavBar) {
                    Surface(shadowElevation = 3.dp) {
                        AnifoxNavigationBar(
                            modifier = Modifier.height(60.dp),
                        ) {
                            appState.topLevelDestinations.forEach { destination ->
                                val selected = currentDestination
                                    .isTopLevelDestinationInHierarchy(destination)
                                AnifoxNavigationBarItem(
                                    icon = {
                                        AnifoxIcon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(destination.icon),
                                            contentDescription = null,
                                        )
                                    },
                                    selected = selected,
                                    label = {
                                        Text(
                                            text = stringResource(destination.iconTextId),
                                            style = MaterialTheme.typography.titleSmall,
                                        )
                                    },
                                    onClick = { appState.navigateToTopLevelDestination(destination) },
                                )
                            }
                        }
                    }
                }
            }
        ) { padding ->
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                Column(Modifier.fillMaxSize()) {
                    AnifoxNavHost(
                        appState = appState,
                        isFirstLaunch = appState.isFirstLaunch,
                    )
                }
            }
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false