package club.anifox.android.data.network.mappers.anime.characters

import club.anifox.android.data.network.models.dto.anime.characters.AnimeCharactersLightDTO
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight

fun AnimeCharactersLightDTO.toLight(): AnimeCharactersLight = AnimeCharactersLight(
    id = id,
    role = role,
    image = image,
    name = name,
)