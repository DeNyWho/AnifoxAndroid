package su.anifox.domain.model.anime.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class AnimeGenre(
    val id: String = "",
    val image: String = "",
    val name: String = "",
)