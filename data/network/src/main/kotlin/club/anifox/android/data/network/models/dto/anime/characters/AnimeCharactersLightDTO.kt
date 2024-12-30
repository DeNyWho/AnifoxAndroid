package club.anifox.android.data.network.models.dto.anime.characters

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeCharactersLightDTO(
    @SerialName("id")
    val id: String,
    @SerialName("role")
    val role: String,
    @SerialName("image")
    val image: String,
    @SerialName("name")
    val name: String,
)
