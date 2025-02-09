package club.anifox.android.feature.translations.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount
import club.anifox.android.feature.translations.component.item.param.TranslationComponentItemProvider
import club.anifox.android.feature.translations.component.item.param.TranslationComponentItemPreviewParam

@Composable
internal fun TranslationComponentItem(
    translation: AnimeTranslationsCount,
    onClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick.invoke(translation.translation.id)
            }
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = translation.translation.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
            Text(
                text = "${translation.countEpisodes} эп.",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .background(Color.LightGray.copy(0.1f))
                .fillMaxWidth(),
        )
    }
}


@Preview
@Composable
private fun PreviewTranslationComponentItem(
    @PreviewParameter(TranslationComponentItemProvider::class) param: TranslationComponentItemPreviewParam,
) {
    DefaultPreview {
        TranslationComponentItem(
            translation = param.animeTranslationsCount,
            onClick = param.onClick,
        )
    }
}