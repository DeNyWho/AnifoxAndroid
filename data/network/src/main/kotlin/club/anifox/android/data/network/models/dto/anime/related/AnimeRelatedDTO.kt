package club.anifox.android.data.network.models.dto.anime.related

import club.anifox.android.data.network.models.dto.anime.light.AnimeLightDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRelatedDTO(
    @SerialName("anime")
    val anime: AnimeLightDTO,
    @SerialName("relation")
    val relation: RelationDTO,
)
