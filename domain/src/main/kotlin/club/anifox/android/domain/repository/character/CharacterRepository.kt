package club.anifox.android.domain.repository.character

import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.state.StateWrapper
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacterFull(id: String): Flow<StateWrapper<CharacterFull>>
}