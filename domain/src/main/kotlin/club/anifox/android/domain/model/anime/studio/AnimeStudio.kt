package club.anifox.android.domain.model.anime.studio

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class AnimeStudio(
    val id: String = "",
    val name: String = "",
)
