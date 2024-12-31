package club.anifox.android.data.network.mappers.character

import club.anifox.android.data.network.mappers.anime.light.toLight
import club.anifox.android.data.network.models.dto.character.full.CharacterFullDTO
import club.anifox.android.data.network.models.dto.character.role.CharacterRoleDTO
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.model.character.role.CharacterRole

fun CharacterFullDTO.toFull(): CharacterFull = CharacterFull(
    id = id,
    name = name,
    nameEn = nameEn,
    nameKanji = nameKanji,
    image = image,
    about = about,
    pictures = pictures,
    roles = roles.map { it.toRole() },
)

fun CharacterRoleDTO.toRole(): CharacterRole = CharacterRole(
    role = role,
    anime = anime.toLight(),
)