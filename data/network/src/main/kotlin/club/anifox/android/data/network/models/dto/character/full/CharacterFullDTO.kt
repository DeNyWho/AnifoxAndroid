package club.anifox.android.data.network.models.dto.character.full

import club.anifox.android.data.network.models.dto.character.role.CharacterRoleDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterFullDTO(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("name_en")
    val nameEn: String,
    @SerialName("name_kanji")
    val nameKanji: String?,
    @SerialName("image")
    val image: String,
    @SerialName("about")
    val about: String?,
    @SerialName("pictures")
    val pictures: List<String>,
    @SerialName("roles")
    val roles: List<CharacterRoleDTO>,
)
