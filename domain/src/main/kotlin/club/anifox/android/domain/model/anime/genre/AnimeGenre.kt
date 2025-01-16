package club.anifox.android.domain.model.anime.genre

import kotlinx.serialization.Serializable

@Serializable
data class AnimeGenre(
    val id: String = "",
    val name: String = "",
)