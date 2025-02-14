package club.anifox.android.feature.translations.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount
import club.anifox.android.domain.state.StateListWrapper

internal data class TranslationsUIPreviewParam(
    val onBackPressed: () -> Unit = { },
    val animeTranslationsCount: StateListWrapper<AnimeTranslationsCount>,
    val onTranslationClick: (Int) -> Unit = { },
)

internal class TranslationsUIProvider:
    PreviewParameterProvider<TranslationsUIPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<TranslationsUIPreviewParam>
        get() = listOf(
            TranslationsUIPreviewParam(
                animeTranslationsCount = StateListWrapper.loading(),
            ),
            TranslationsUIPreviewParam(
                animeTranslationsCount = StateListWrapper(data = GlobalParams.TranslationsCount),
            ),
        ).asSequence()
}