package club.anifox.android.feature.translations.composable.item.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount

internal data class TranslationItemPreviewParam(
    val onClick: (Int) -> Unit = { },
    val animeTranslationsCount: AnimeTranslationsCount,
)

internal class TranslationItemContentProvider:
    PreviewParameterProvider<TranslationItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<TranslationItemPreviewParam>
        get() = listOf(
            TranslationItemPreviewParam(
                animeTranslationsCount = GlobalParams.TranslationsCount.first(),
            ),
        ).asSequence()
}