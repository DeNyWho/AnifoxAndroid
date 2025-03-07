package club.anifox.android.feature.translations.component.item.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount

internal data class TranslationComponentItemPreviewParam(
    val onClick: () -> Unit = { },
    val animeTranslationsCount: AnimeTranslationsCount,
)

internal class TranslationComponentItemProvider :
    PreviewParameterProvider<TranslationComponentItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<TranslationComponentItemPreviewParam>
        get() = listOf(
            TranslationComponentItemPreviewParam(
                animeTranslationsCount = GlobalParams.TranslationsCount.first(),
            ),
        ).asSequence()
}