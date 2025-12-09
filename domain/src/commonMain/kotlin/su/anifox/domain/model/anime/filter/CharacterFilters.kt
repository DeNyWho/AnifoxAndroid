package su.anifox.domain.model.anime.filter

import su.anifox.domain.model.anime.enum.character.CharacterRole

data class CharacterFilters(
    val animeUrl: String? = null,
    val search: String? = null,
    val role: CharacterRole? = null,
    val page: Int = 0,
    val limit: Int = 30,
)