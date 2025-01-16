package club.anifox.android.domain.model.anime.studio

import kotlinx.serialization.Serializable

@Serializable
data class AnimeStudio(
    val id: String = "",
    val name: String = "",
)
