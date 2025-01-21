package club.anifox.android.data.source.repository.anime

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import club.anifox.android.data.local.cache.dao.anime.catalog.AnimeCacheCatalogDao
import club.anifox.android.data.local.cache.dao.anime.characters.AnimeCacheCharactersAvailableRolesDao
import club.anifox.android.data.local.cache.dao.anime.characters.AnimeCacheCharactersDao
import club.anifox.android.data.local.cache.dao.anime.episodes.AnimeCacheEpisodesDao
import club.anifox.android.data.local.cache.dao.anime.genres.AnimeCacheGenresDao
import club.anifox.android.data.local.cache.dao.anime.schedule.AnimeCacheScheduleDao
import club.anifox.android.data.local.cache.dao.anime.search.AnimeCacheSearchDao
import club.anifox.android.data.local.dao.anime.search.AnimeSearchHistoryDao
import club.anifox.android.data.local.mappers.cache.anime.toLight
import club.anifox.android.data.local.model.anime.search.AnimeSearchHistoryEntity
import club.anifox.android.data.network.mappers.anime.characters.toLight
import club.anifox.android.data.network.mappers.anime.common.toGenre
import club.anifox.android.data.network.mappers.anime.common.toStudio
import club.anifox.android.data.network.mappers.anime.detail.toDetail
import club.anifox.android.data.network.mappers.anime.episodes.toTranslation
import club.anifox.android.data.network.mappers.anime.episodes.toTranslationsCount
import club.anifox.android.data.network.mappers.anime.light.toLight
import club.anifox.android.data.network.mappers.anime.related.toLight
import club.anifox.android.data.network.mappers.anime.videos.toLight
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.paging.anime.catalog.AnimeCatalogRemoteMediator
import club.anifox.android.data.source.paging.anime.characters.AnimeCharactersRemoteMediator
import club.anifox.android.data.source.paging.anime.episodes.AnimeEpisodesRemoteMediator
import club.anifox.android.data.source.paging.anime.genres.AnimeGenresRemoteMediator
import club.anifox.android.data.source.paging.anime.schedule.AnimeScheduleRemoteMediator
import club.anifox.android.data.source.paging.anime.search.AnimeSearchRemoteMediator
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.VideoType
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation
import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.model.common.enum.WeekDay
import club.anifox.android.domain.model.common.request.Resource
import club.anifox.android.domain.repository.anime.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

internal class AnimeRepositoryImpl @Inject constructor(
    private val animeService: AnimeService,
    private val animeSearchHistoryDao: AnimeSearchHistoryDao,
    private val animeCacheSearchDao: AnimeCacheSearchDao,
    private val animeCacheCatalogDao: AnimeCacheCatalogDao,
    private val animeCacheGenresDao: AnimeCacheGenresDao,
    private val animeCacheEpisodesDao: AnimeCacheEpisodesDao,
    private val animeCacheScheduleDao: AnimeCacheScheduleDao,
    private val animeCacheCharactersDao: AnimeCacheCharactersDao,
    private val animeCacheCharactersAvailableRolesDao: AnimeCacheCharactersAvailableRolesDao,
) : AnimeRepository {

    override fun getAnime(
        page: Int,
        limit: Int,
        status: AnimeStatus?,
        genres: List<String>?,
        searchQuery: String?,
        season: AnimeSeason?,
        ratingMpa: String?,
        minimalAge: Int?,
        type: AnimeType?,
        years: List<Int>?,
        studios: List<String>?,
        order: AnimeOrder?,
        sort: AnimeSort?,
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
                years = years,
                studios = studios,
                order = order,
                sort = sort,
            )

            val state = when (animeResult) {
                is Resource.Success -> {
                    val data = animeResult.data.map { it.toLight() }.toImmutableList()
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

    override fun getLastSearchesHistory(): Flow<List<String>> =
        animeSearchHistoryDao.getLastSearches()
            .map { entities -> entities.map { it.query } }

    override suspend fun addSearchHistory(query: String) {
        animeSearchHistoryDao.insertSearch(AnimeSearchHistoryEntity(query = query))
        animeSearchHistoryDao.keepOnly10LastSearches()
    }

    override suspend fun deleteSearchHistory() {
        animeSearchHistoryDao.deleteSearch()
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAnimeSearchPaged(
        limit: Int,
        searchQuery: String?,
    ): Flow<PagingData<AnimeLight>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = AnimeSearchRemoteMediator(
                animeService = animeService,
                animeCacheSearchDao = animeCacheSearchDao,
                searchQuery = searchQuery,
            ),
            pagingSourceFactory = { animeCacheSearchDao.pagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toLight() }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAnimeCatalogPaged(
        limit: Int,
        status: AnimeStatus?,
        genres: List<String>?,
        searchQuery: String?,
        season: AnimeSeason?,
        ratingMpa: String?,
        minimalAge: Int?,
        type: AnimeType?,
        years: List<Int>?,
        studios: List<String>?,
        translation: List<Int>?,
        order: AnimeOrder?,
        sort: AnimeSort?,
    ): Flow<PagingData<AnimeLight>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = AnimeCatalogRemoteMediator(
                animeService = animeService,
                animeCacheCatalogDao = animeCacheCatalogDao,
                status = status,
                genres = genres,
                searchQuery = searchQuery,
                season = season,
                ratingMpa = ratingMpa,
                minimalAge = minimalAge,
                type = type,
                years = years,
                studios = studios,
                translation = translation,
                order = order,
                sort = sort,
            ),
            pagingSourceFactory = { animeCacheCatalogDao.pagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toLight() }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAnimeGenresPaged(
        limit: Int,
        genre: String,
        minimalAge: Int?,
    ): Flow<PagingData<AnimeLight>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = AnimeGenresRemoteMediator(
                animeService = animeService,
                animeCacheGenresDao = animeCacheGenresDao,
                genre = genre,
                minimalAge = minimalAge,
            ),
            pagingSourceFactory = { animeCacheGenresDao.pagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toLight() }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAnimeEpisodesPaged(
        limit: Int,
        url: String,
        translationId: Int,
    ): Flow<PagingData<AnimeEpisodesLight>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = AnimeEpisodesRemoteMediator(
                animeService = animeService,
                animeCacheEpisodesDao = animeCacheEpisodesDao,
                url = url,
                translationId = translationId,
            ),
            pagingSourceFactory = { animeCacheEpisodesDao.getPagedEpisodes() }
        ).flow.map { pagingData ->
            pagingData.map { it.toLight() }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAnimeCharactersPaged(
        limit: Int,
        url: String,
        role: String?,
    ): Flow<PagingData<AnimeCharactersLight>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            remoteMediator = AnimeCharactersRemoteMediator(
                animeService = animeService,
                animeCacheCharactersDao = animeCacheCharactersDao,
                animeCacheCharactersAvailableRolesDao = animeCacheCharactersAvailableRolesDao,
                url = url,
                role = role,
            ),
            pagingSourceFactory = { animeCacheCharactersDao.pagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toLight() }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAnimeScheduleForDayPaged(
        dayOfWeek: WeekDay,
        date: LocalDate,
    ): Flow<PagingData<AnimeLight>> {
        return Pager(
            config = PagingConfig(
                pageSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 100,
            ),
            remoteMediator = AnimeScheduleRemoteMediator(
                dayOfWeek = dayOfWeek,
                date = date,
                animeService = animeService,
                animeCacheScheduleDao = animeCacheScheduleDao
            ),
            pagingSourceFactory = { animeCacheScheduleDao.getPagedAnimeForDay(dayOfWeek.name) }
        ).flow.map { pagingData ->
            pagingData.map { it.toLight() }
        }
    }

    override fun getAnimeGenres(): Flow<StateListWrapper<AnimeGenre>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when(val genreResult = animeService.getAnimeGenres()) {
                is Resource.Success -> {
                    val data = genreResult.data.map { it.toGenre() }.toImmutableList()
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = genreResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAnimeYears(): Flow<StateListWrapper<Int>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when(val yearsResult = animeService.getAnimeYears()) {
                is Resource.Success -> {
                    val data = yearsResult.data.toImmutableList()
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = yearsResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAnimeStudios(): Flow<StateListWrapper<AnimeStudio>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when(val studioResult = animeService.getAnimeStudios()) {
                is Resource.Success -> {
                    val data = studioResult.data.map { it.toStudio() }.toImmutableList()
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = studioResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAnimeTranslations(): Flow<StateListWrapper<AnimeTranslation>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when(val translationsResult = animeService.getAnimeTranslations()) {
                is Resource.Success -> {
                    val data = translationsResult.data.map { it.toTranslation() }.toImmutableList()
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = translationsResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAnimeTranslationsCount(url: String): Flow<StateListWrapper<AnimeTranslationsCount>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when(val translationsCountResult = animeService.getAnimeTranslationsCount(url)) {
                is Resource.Success -> {
                    val data = translationsCountResult.data.map { it.toTranslationsCount() }.toImmutableList()
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = translationsCountResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAnimeDetail(url: String): Flow<StateWrapper<AnimeDetail>> {
        return flow {
            emit(StateWrapper.loading())

            val animeResult = animeService.getAnimeDetail(
                url = url
            )

            val state = when (animeResult) {
                is Resource.Success -> {
                    val data = animeResult.data.toDetail()
                    StateWrapper(data)
                }
                is Resource.Error -> {
                    StateWrapper(error = animeResult.error)
                }
                is Resource.Loading -> {
                    StateWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAnimeCharacters(
        url: String,
        page: Int,
        limit: Int,
    ): Flow<StateListWrapper<AnimeCharactersLight>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when (val animeCharactersResult = animeService.getAnimeCharacters(
                page = page,
                limit = limit,
                url = url,
                role = null,
            )) {
                is Resource.Success -> {
                    val data = animeCharactersResult.data.characters.map { it.toLight() }.toImmutableList()
                    StateListWrapper(data)
                }

                is Resource.Error -> {
                    StateListWrapper(error = animeCharactersResult.error)
                }

                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAnimeSimilar(url: String): Flow<StateListWrapper<AnimeLight>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when (val animeSimilarResult = animeService.getAnimeSimilar(url)) {
                is Resource.Success -> {
                    val data = animeSimilarResult.data.map { it.toLight() }.toImmutableList()
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = animeSimilarResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAnimeRelated(url: String): Flow<StateListWrapper<AnimeRelatedLight>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when (val animeRelatedResult = animeService.getAnimeRelated(url)) {
                is Resource.Success -> {
                    val data = animeRelatedResult.data.map { it.toLight() }.toImmutableList()
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = animeRelatedResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAnimeScreenshots(url: String, limit: Int?): Flow<StateListWrapper<String>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when (val animeScreenshotsResult = animeService.getAnimeScreenshots(url)) {
                is Resource.Success -> {
                    val data = if(limit != null) {
                        animeScreenshotsResult.data.take(limit)
                    } else {
                        animeScreenshotsResult.data
                    }.toImmutableList()
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = animeScreenshotsResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAnimeVideos(url: String, videoType: VideoType?, limit: Int?): Flow<StateListWrapper<AnimeVideosLight>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when (val animeVideosResult = animeService.getAnimeVideos(url, videoType)) {
                is Resource.Success -> {
                    val data = if(limit == null) {
                        animeVideosResult.data.map { it.toLight() }
                    } else {
                        animeVideosResult.data.take(limit).map { it.toLight() }
                    }.toImmutableList()
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = animeVideosResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }
}