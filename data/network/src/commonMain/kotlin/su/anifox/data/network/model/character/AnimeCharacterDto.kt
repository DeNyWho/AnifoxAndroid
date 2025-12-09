package su.anifox.data.network.model.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeCharacterDto(
    @SerialName("id")
    val id: String,
    @SerialName("malId")
    val malId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("nameEn")
    val nameEn: String,
    @SerialName("nameKanji")
    val nameKanji: String? = null,
    @SerialName("image")
    val image: String,
    @SerialName("about")
    val about: String? = null,
    @SerialName("aboutEn")
    val aboutEn: String? = null,
    @SerialName("pictures")
    val pictures: List<String> = emptyList(),
    @SerialName("role")
    val role: String,
)