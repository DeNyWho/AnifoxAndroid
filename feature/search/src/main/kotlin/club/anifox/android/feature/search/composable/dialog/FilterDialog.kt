package club.anifox.android.feature.search.composable.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.search.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FilterDialog(
    sheetState: SheetState,
    onDismissRequest: () -> Unit = { },
    animeYears: StateListWrapper<Int>,
    animeStudios: StateListWrapper<AnimeStudio>,
    animeTranslations: StateListWrapper<AnimeTranslation>,
) {
    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = sheetState,
        tonalElevation = 10.dp,
        onDismissRequest = {
            onDismissRequest.invoke()
        },
        windowInsets = WindowInsets(top = 30.dp),
        scrimColor = Color.Black.copy(alpha = 0.5f),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
                    .height(4.dp)
                    .clip(shape = MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.onBackground)
                    .width(96.dp),
            )
        },
    ) {
        SheetContent(
            animeYears = animeYears,
            animeStudios = animeStudios,
            animeTranslations = animeTranslations,
        )
    }
}

@Composable
private fun SheetContent(
    animeYears: StateListWrapper<Int>,
    animeStudios: StateListWrapper<AnimeStudio>,
    animeTranslations: StateListWrapper<AnimeTranslation>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 12.dp, end = 12.dp, bottom = 20.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.feature_search_filter_button),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(R.string.feature_search_filter_status_title),
            style = MaterialTheme.typography.titleLarge,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {

        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewSheetContent() {
    SheetContent(StateListWrapper.loading(),StateListWrapper.loading(),StateListWrapper.loading())
}