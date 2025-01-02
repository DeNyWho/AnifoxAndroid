package club.anifox.android.feature.detail.components.description

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnBackground
import club.anifox.android.core.uikit.component.slider.SliderComponentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.R

@Composable
internal fun DescriptionComponent(
    modifier: Modifier,
    detailAnimeState: StateWrapper<AnimeDetail>,
    headerModifier: Modifier = SliderComponentDefaults.BottomOnly,
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
) {
    if (!detailAnimeState.isLoading) {
        var isTextExceedingMaxLines by remember { mutableStateOf(false) }

        if(detailAnimeState.data?.description?.isNotEmpty() == true) {
            SliderHeader(
                modifier = headerModifier,
                title = stringResource(R.string.feature_detail_section_header_title_description),
            )
        }

        if (isTextExceedingMaxLines) {
            AnimatedContent(
                targetState = isExpanded,
                transitionSpec = {
                    expandVertically(animationSpec = tween(150, 150), initialHeight = { it }) togetherWith
                        shrinkVertically(animationSpec = tween(150, 0), targetHeight = { it }) using
                        SizeTransform(clip = true)
                },
                label = "",
            ) { targetExpanded ->
                Column(
                    modifier = modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onExpandedChanged(!isExpanded) },
                        ),
                ) {
                    if (targetExpanded) {
                        Text(
                            text = detailAnimeState.data?.description ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Justify,
                        )
                        AnifoxIconOnBackground(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterHorizontally),
                            imageVector = Filled.KeyboardArrowUp,
                            contentDescription = "Switch",
                        )
                    } else {
                        Text(
                            text = detailAnimeState.data?.description ?: "",
                            maxLines = 5,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Justify,
                        )
                        AnifoxIconOnBackground(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterHorizontally),
                            imageVector = Filled.KeyboardArrowDown,
                            contentDescription = "Expand",
                        )
                    }
                }
            }
        } else {
            if (detailAnimeState.data?.description?.isNotEmpty() == true) {
                Text(
                    modifier = modifier,
                    text = detailAnimeState.data?.description ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify,
                    onTextLayout = { textLayoutResult: TextLayoutResult ->
                        isTextExceedingMaxLines = textLayoutResult.lineCount > 5
                    },
                )
            }
        }
    }

}