package club.anifox.android.feature.detail.components.description

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import club.anifox.android.commonui.component.icon.AnifoxIconOnBackground
import club.anifox.android.commonui.component.icon.AnifoxIconPrimary
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper

@Composable
internal fun DescriptionContent(
    detailAnimeState: StateWrapper<AnimeDetail>,
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
) {
    if(!detailAnimeState.isLoading) {
        val descriptionGradient = Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.background.copy(alpha = 0F),
                MaterialTheme.colorScheme.background.copy(alpha = 0.9F),
                MaterialTheme.colorScheme.background,
            )
        )

        val visible = if(detailAnimeState.data?.description?.isNotEmpty() == true) detailAnimeState.data?.description?.length!! > 300 else false

        if (visible) {
            AnimatedContent(
                targetState = isExpanded,
                transitionSpec = {
                    expandVertically(animationSpec = tween(150, 150), initialHeight = { it }) togetherWith
                        shrinkVertically(animationSpec = tween(150, 0), targetHeight = { it }) using
                        SizeTransform(clip = true)
                },
                label = "",
            ) { targetExpanded ->
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onExpandedChanged(!isExpanded) }
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
                            imageVector = Filled.KeyboardArrowUp,
                            contentDescription = "Switch",
                            modifier = Modifier.align(Alignment.BottomCenter),
                        )
                    } else {
                        Text(
                            text = detailAnimeState.data?.description ?: "",
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Justify,
                        )
                        Box(
                            modifier = Modifier
                                .zIndex(1F)
                                .fillMaxSize()
                                .align(Alignment.BottomCenter)
                                .background(
                                    brush = descriptionGradient
                                ),
                        ) {
                            AnifoxIconOnBackground(
                                imageVector = Filled.KeyboardArrowDown,
                                contentDescription = "Expand",
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .zIndex(2F),
                            )
                        }
                    }
                }
            }
        } else {
            Text(
                text = detailAnimeState.data?.description ?: "",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
            )
        }
    }
}