package club.anifox.android.domain.repository

import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getAnime(
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
        filter: FilterEnum?,
    ): Flow<StateListWrapper<AnimeLight>>

    fun getAnimeDetail(
        url: String
    ): Flow<StateWrapper<AnimeDetail>>
}