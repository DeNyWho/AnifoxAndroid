package club.anifox.android.data.network.models.dto.character.role

import club.anifox.android.data.network.models.dto.anime.light.AnimeLightDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterRoleDTO(
    @SerialName("role")
    val role: String,
    @SerialName("anime")
    val anime: AnimeLightDTO,
)