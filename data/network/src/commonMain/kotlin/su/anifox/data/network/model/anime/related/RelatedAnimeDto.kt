package su.anifox.data.network.model.anime.related

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import su.anifox.data.network.model.anime.common.AnimeLightDto

@Serializable
internal data class RelatedAnimeDto(
    @SerialName("anime")
    val anime: AnimeLightDto,
    @SerialName("type")
    val type: String,
)