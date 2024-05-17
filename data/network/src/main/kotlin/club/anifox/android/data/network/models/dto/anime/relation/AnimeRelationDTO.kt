package club.anifox.android.data.network.models.dto.anime.relation

import club.anifox.android.data.network.models.dto.anime.light.AnimeLightDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRelationDTO(
    @SerialName("anime")
    val anime: AnimeLightDTO = AnimeLightDTO(),
    @SerialName("relation")
    val relation: RelationDTO = RelationDTO(),
)