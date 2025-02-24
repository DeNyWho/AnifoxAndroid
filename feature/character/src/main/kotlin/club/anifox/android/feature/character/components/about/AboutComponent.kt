package club.anifox.android.feature.character.components.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.component.text.ExpandedText
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.feature.character.R

@Composable
internal fun AboutComponent(
    modifier: Modifier = Modifier,
    character: CharacterFull,
    headerModifier: Modifier = Modifier,
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
) {
    SliderHeader(
        modifier = headerModifier,
        title = stringResource(R.string.feature_character_about_header_title),
    )

    ExpandedText(
        modifier = modifier,
        text = character.about.orEmpty(),
        isExpanded = isExpanded,
        onExpandedChanged = onExpandedChanged,
    )
}