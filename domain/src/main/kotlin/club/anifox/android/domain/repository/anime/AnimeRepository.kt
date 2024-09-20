package club.anifox.android.domain.repository.anime

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.model.anime.enum.VideoType
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
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
        minimalAge: Int?,
        type: AnimeType?,
        year: Int?,
        studio: String?,
        filter: FilterEnum?,
    ): Flow<StateListWrapper<AnimeLight>>

    fun getAnimeGenres(): Flow<StateListWrapper<AnimeGenre>>

    fun getAnimeDetail(
        url: String
    ): Flow<StateWrapper<AnimeDetail>>

    fun getAnimeSimilar(url: String): Flow<StateListWrapper<AnimeLight>>
    fun getAnimeRelated(url: String): Flow<StateListWrapper<AnimeRelatedLight>>
    fun getAnimeScreenshots(url: String, limit: Int?): Flow<StateListWrapper<String>>
    fun getAnimeVideos(url: String, videoType: VideoType?, limit: Int?): Flow<StateListWrapper<AnimeVideosLight>>

    fun getAnimeSearchPaged(
        limit: Int,
        status: AnimeStatus?,
        genres: List<String>?,
        searchQuery: String?,
        season: AnimeSeason?,
        ratingMpa: String?,
        minimalAge: Int?,
        type: AnimeType?,
        year: Int?,
        studio: String?,
        translation: List<Int>?,
        filter: FilterEnum?
    ): Flow<PagingData<AnimeLight>>

    fun getAnimeGenresPaged(
        limit: Int,
        genre: String,
        minimalAge: Int?,
        filter: FilterEnum?
    ): Flow<PagingData<AnimeLight>>

    fun getAnimeYears(): Flow<StateListWrapper<Int>>
    fun getAnimeStudios(): Flow<StateListWrapper<AnimeStudio>>
    fun getAnimeTranslations(): Flow<StateListWrapper<AnimeTranslation>>
}