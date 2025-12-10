package su.anifox.data.network.mappers.characters

import su.anifox.data.network.model.character.AnimeCharacterDto
import su.anifox.domain.model.anime.enum.character.CharacterRole
import su.anifox.domain.model.characters.AnimeCharactersLight

internal object CharacterMapper {
    fun AnimeCharacterDto.toDomain(): AnimeCharactersLight {
        return AnimeCharactersLight(
            id = id,
            name = name,
            image = image,
            role = CharacterRole.valueOf(role),
        )
    }
}