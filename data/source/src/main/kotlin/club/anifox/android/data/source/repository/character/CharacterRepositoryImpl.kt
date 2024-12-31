package club.anifox.android.data.source.repository.character

import club.anifox.android.data.network.mappers.character.toFull
import club.anifox.android.data.network.service.CharacterService
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.model.common.request.Resource
import club.anifox.android.domain.repository.character.CharacterRepository
import club.anifox.android.domain.state.StateWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService,
): CharacterRepository {

    override fun getCharacterFull(id: String): Flow<StateWrapper<CharacterFull>> {
        return flow {
            emit(StateWrapper.loading())

            val state = when (val characterResult = characterService.getCharacterFull(id)) {
                is Resource.Success -> {
                    val data = characterResult.data.toFull()
                    StateWrapper(data)
                }
                is Resource.Error -> {
                    StateWrapper(error = characterResult.error)
                }
                is Resource.Loading -> {
                    StateWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }
}