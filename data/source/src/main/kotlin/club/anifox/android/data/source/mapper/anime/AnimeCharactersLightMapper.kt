package club.anifox.android.data.source.mapper.anime

import club.anifox.android.data.local.cache.model.anime.characters.AnimeCacheCharactersEntity
import club.anifox.android.data.network.models.dto.anime.characters.AnimeCharactersLightDTO

fun AnimeCharactersLightDTO.toEntity(): AnimeCacheCharactersEntity = AnimeCacheCharactersEntity(
    id = id,
    role = role,
    image = image,
    name = name,
)