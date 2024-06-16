package club.anifox.android.data.network.models.dto.anime.related

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelationDTO(
    @SerialName("type")
    val type: String,
)