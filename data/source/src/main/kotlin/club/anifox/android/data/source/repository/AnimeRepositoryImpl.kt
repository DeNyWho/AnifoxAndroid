package club.anifox.android.data.source.repository

import club.anifox.android.data.network.mappers.anime.light.toLight
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.model.common.Resource
import club.anifox.android.domain.repository.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeService: AnimeService,
) : AnimeRepository {

    override fun getAnime(
        page: Int,
        limit: Int,
        status: AnimeStatus?,
        genres: List<String>?,
        searchQuery: String?,
        season: AnimeSeason?,
        ratingMpa: String?,
        minimalAge: String?,
        type: AnimeType?,
        year: Int?,
        studio: String?,
        filter: FilterEnum?
    ): Flow<StateListWrapper<AnimeLight>> {
        return flow {
            emit(StateListWrapper.loading())

            val animeResult = animeService.getAnime(
                page = page,
                limit = limit,
                status = status,
                genres = genres,
                searchQuery = searchQuery,
                season = season,
                ratingMpa = ratingMpa,
                minimalAge = minimalAge,
                type = type,
                year = year,
                studio = studio,
                filter = filter
            )

            val state = when (animeResult) {
                is Resource.Success -> {
                    val data = animeResult.data.map { it.toLight() }
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = animeResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }
}