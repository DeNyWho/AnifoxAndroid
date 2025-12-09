package su.anifox.domain.model.anime.related

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import su.anifox.domain.model.anime.common.AnimeLight

@Immutable
data class AnimeRelations(
    val franchise: String? = null,
    val related: ImmutableList<RelatedAnime> = persistentListOf(),
    val similar: ImmutableList<AnimeLight> = persistentListOf(),
)