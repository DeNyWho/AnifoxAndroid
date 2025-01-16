package club.anifox.android.domain.model.anime.image

import kotlinx.serialization.Serializable

@Serializable
data class AnimeImage(
    val large: String = "",
    val medium: String = "",
)