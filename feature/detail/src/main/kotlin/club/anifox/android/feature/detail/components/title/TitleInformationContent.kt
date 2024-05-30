package club.anifox.android.feature.detail.components.title

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import club.anifox.android.commonui.component.icon.AnifoxIcon
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.R
import club.anifox.android.feature.detail.components.title.param.TitleInformationContentPreviewParam
import club.anifox.android.feature.detail.components.title.param.TitleInformationContentProvider

@Composable
internal fun TitleInformationContent(
    detailAnime: StateWrapper<AnimeDetail>,
) {
    val showDialog = remember { mutableStateOf(false) }

    if(!detailAnime.isLoading) {
        if(showDialog.value)
            TitleDialog(
                title = detailAnime.data?.title ?: "",
                engTitle = detailAnime.data?.titleEnglish?.get(0) ?: "",
                japaneseTitle = detailAnime.data?.titleJapan?.joinToString(", ") ?: "",
                synonymsTitle = detailAnime.data?.synonyms?.joinToString(", ") ?: "",
            ) {
                showDialog.value = it
            }

        Row(
            modifier = Modifier
                .clickable {
                    showDialog.value = true
                }
                .fillMaxWidth()
        ) {
            AnifoxIcon(
                Outlined.Info,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(20.dp),
                contentDescription = stringResource(R.string.feature_detail_content_description_info),
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = detailAnime.data?.title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = detailAnime.data?.titleEnglish?.get(0) ?: "",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTitleInformationContent(
    @PreviewParameter(TitleInformationContentProvider::class) param: TitleInformationContentPreviewParam,
) {
    TitleInformationContent(param.detailAnime)
}
