package club.anifox.android.domain.usecase.character

import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.repository.character.CharacterRepository
import club.anifox.android.domain.state.StateWrapper
import kotlinx.coroutines.flow.Flow

class GetCharacterFullUseCase(private val characterRepository: CharacterRepository) {
    operator fun invoke(id: String): Flow<StateWrapper<CharacterFull>> {
        return characterRepository.getCharacterFull(id)
    }
}