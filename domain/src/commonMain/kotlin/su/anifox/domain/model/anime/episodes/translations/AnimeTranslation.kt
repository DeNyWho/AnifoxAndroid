package su.anifox.domain.model.anime.episodes.translations

import androidx.compose.runtime.Immutable
import su.anifox.domain.model.anime.enum.TranslationType

@Immutable
data class AnimeTranslation(
    val id: Int = 0,
    val title: String = "",
    val type: TranslationType
)