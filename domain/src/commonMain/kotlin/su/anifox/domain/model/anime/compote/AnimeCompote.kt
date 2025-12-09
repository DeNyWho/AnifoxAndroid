package su.anifox.domain.model.anime.compote

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import su.anifox.domain.model.anime.common.AnimeGenre
import su.anifox.domain.model.anime.common.AnimeStudio

@Immutable
data class AnimeCompote(
    val animeId: String,
    val genres: ImmutableList<AnimeGenre>,
    val studios: ImmutableList<AnimeStudio>,
)