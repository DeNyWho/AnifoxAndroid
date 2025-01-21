package club.anifox.android.domain.model.anime.genre

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class AnimeGenre(
    val id: String = "",
    val name: String = "",
)