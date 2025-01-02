package club.anifox.android.feature.character.components.anime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortrait
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.domain.model.character.role.CharacterRole
import club.anifox.android.feature.character.R

@Composable
internal fun AnimeComponent(
    headerModifier: Modifier = SliderContentDefaults.BottomOnly,
    itemModifier: Modifier = Modifier.width(CardAnimePortraitDefaults.Width.Default),
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.Default,
    thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.Default,
    roles: List<CharacterRole>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    contentArrangement: Arrangement.Horizontal = CardAnimePortraitDefaults.HorizontalArrangement.Default,
    textAlign: TextAlign = TextAlign.Start,
    onItemClick: (String) -> Unit,
) {
    SliderHeader(
        modifier = headerModifier,
        title = stringResource(R.string.feature_character_anime_header_title),
        isMoreVisible = false,
    )

    LazyRow(
        contentPadding = contentPadding,
        horizontalArrangement = contentArrangement,
    ) {
        items(
            roles,
            key = { it.anime.url },
        ) { data ->
            CardAnimePortrait(
                modifier = itemModifier,
                data = data.anime,
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
                textAlign = textAlign,
                onClick = { onItemClick.invoke(data.anime.url) },
            )
        }
    }
}