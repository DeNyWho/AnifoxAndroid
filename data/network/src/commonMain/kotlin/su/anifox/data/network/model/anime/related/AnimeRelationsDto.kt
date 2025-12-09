package su.anifox.data.network.model.anime.related

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import su.anifox.data.network.model.anime.common.AnimeLightDto
import su.anifox.data.network.model.common.AnifoxResponseDto

@Serializable
data class AnimeRelationsDto(
    @SerialName("franchise")
    val franchise: String? = null,
    @SerialName("related")
    val related: AnifoxResponseDto<RelatedAnimeDto>,
    @SerialName("similar")
    val similar: AnifoxResponseDto<AnimeLightDto>,
)