package su.anifox.domain.model.characters

import androidx.compose.runtime.Immutable
import su.anifox.domain.model.anime.enum.character.CharacterRole

@Immutable
data class AnimeCharactersLight(
    val id: String,
    val image: String,
    val name: String,
    val role: CharacterRole,
)
