package club.anifox.android.feature.translations.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount
import club.anifox.android.domain.state.StateListWrapper

internal data class TranslationsContentPreviewParam(
    val onBackPressed: () -> Boolean = { false },
    val animeTranslationsCount: StateListWrapper<AnimeTranslationsCount>,
    val onTranslationClick: (Int) -> Unit = { },
)

internal class TranslationsContentProvider:
    PreviewParameterProvider<TranslationsContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<TranslationsContentPreviewParam>
        get() = listOf(
            TranslationsContentPreviewParam(
                animeTranslationsCount = StateListWrapper.loading(),
            ),
            TranslationsContentPreviewParam(
                animeTranslationsCount = StateListWrapper(data = GlobalParams.TranslationsCount),
            ),
        ).asSequence()
}