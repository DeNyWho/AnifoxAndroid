package club.anifox.android.data.network.models.dto.anime.characters

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeCharactersWithRolesDTO(
    @SerialName("characters")
    val characters: List<AnimeCharactersLightDTO>,
    @SerialName("available_roles")
    val availableRoles: List<String>,
)