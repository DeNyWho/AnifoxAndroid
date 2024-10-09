package club.anifox.android.feature.translations.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal data class TranslationsContentPreviewParam(
    val onBackPressed: () -> Boolean = { false },
    val animeTitle: String?,
)

internal class TranslationsContentProvider:
    PreviewParameterProvider<TranslationsContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<TranslationsContentPreviewParam>
        get() = listOf(
            TranslationsContentPreviewParam(
                animeTitle = "Провожающая в последний путь Фрирен",
            ),
            TranslationsContentPreviewParam(
                animeTitle = "Провожающая в последний путь Фрирен",
            ),
        ).asSequence()
}