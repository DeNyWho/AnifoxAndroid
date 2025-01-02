package club.anifox.android.feature.detail.components.title

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnSurface
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.R
import club.anifox.android.feature.detail.components.title.param.TitleInformationComponentPreviewParam
import club.anifox.android.feature.detail.components.title.param.TitleInformationComponentProvider

@Composable
internal fun TitleInformationComponent(
    modifier: Modifier = Modifier,
    detailAnimeState: StateWrapper<AnimeDetail>,
) {
    val showDialog = remember { mutableStateOf(false) }

    if(!detailAnimeState.isLoading) {
        if(showDialog.value)
            TitleDialog(
                title = detailAnimeState.data?.title ?: "",
                engTitle = detailAnimeState.data?.titleEnglish?.get(0) ?: "",
                japaneseTitle = detailAnimeState.data?.titleJapan?.joinToString(", ") ?: "",
                synonymsTitle = detailAnimeState.data?.synonyms?.joinToString(", ") ?: "",
            ) {
                showDialog.value = it
            }

        Row(
            modifier = modifier
                .clickable {
                    showDialog.value = true
                }
                .fillMaxWidth()
        ) {
            AnifoxIconOnSurface(
                imageVector = Outlined.Info,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(20.dp),
                contentDescription = stringResource(R.string.feature_detail_content_description_info),
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = detailAnimeState.data?.title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                )
                if(detailAnimeState.data?.titleEnglish?.get(0) != null && detailAnimeState.data?.titleEnglish?.get(0) != "null") {
                    Text(
                        text = detailAnimeState.data?.titleEnglish?.get(0) ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTitleInformationContent(
    @PreviewParameter(TitleInformationComponentProvider::class) param: TitleInformationComponentPreviewParam,
) {
    TitleInformationComponent(
        detailAnimeState = param.detailAnime,
    )
}
