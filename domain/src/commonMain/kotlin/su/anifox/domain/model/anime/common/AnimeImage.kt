package su.anifox.domain.model.anime.common

import kotlinx.serialization.Serializable

@Serializable
data class AnimeImage(
    val large: String = "",
    val medium: String = "",
)