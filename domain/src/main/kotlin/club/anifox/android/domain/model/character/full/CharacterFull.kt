package club.anifox.android.domain.model.character.full

import club.anifox.android.domain.model.character.role.CharacterRole

data class CharacterFull(
    val id: String,
    val name: String,
    val nameEn: String,
    val nameKanji: String?,
    val image: String,
    val about: String,
    val pictures: List<String>,
    val roles: List<CharacterRole>,
)