package club.anifox.android.data.source.paging.anime.episodes

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import club.anifox.android.data.local.cache.dao.anime.episodes.AnimeCacheEpisodeWithTranslations
import club.anifox.android.data.local.cache.dao.anime.episodes.AnimeCacheEpisodesDao
import club.anifox.android.data.local.cache.model.anime.episodes.AnimeCacheEpisodesEntity
import club.anifox.android.data.local.cache.model.anime.translation.AnimeCacheEpisodesTranslationsEntity
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.common.request.Resource

@OptIn(ExperimentalPagingApi::class)
internal class AnimeEpisodesRemoteMediator(
    private val animeService: AnimeService,
    private val animeCacheEpisodesDao: AnimeCacheEpisodesDao,
    private val url: String,
    private val translationId: Int,
    private val sort: AnimeSort,
    private val search: String,
) : RemoteMediator<Int, AnimeCacheEpisodeWithTranslations>() {

    private var lastLoadedPage = -1
    private var currentParams: Params = Params(sort, search)

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private data class Params(
        val sort: AnimeSort,
        val search: String,
    )

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeCacheEpisodeWithTranslations>
    ): MediatorResult {
        val newParams = Params(sort, search)

        if (newParams != currentParams) {
            currentParams = newParams
            lastLoadedPage = -1
            animeCacheEpisodesDao.clearAllEpisodes()
            animeCacheEpisodesDao.clearAllTranslations()
        }

        return try {
            if (newParams != currentParams) {
                currentParams = newParams
                return load(LoadType.REFRESH, state)
            }

            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    animeCacheEpisodesDao.clearAllEpisodes()
                    animeCacheEpisodesDao.clearAllTranslations()
                    lastLoadedPage = -1
                    0
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> lastLoadedPage + 1
            }

            val response = animeService.getAnimeEpisodes(
                page = loadKey,
                limit = state.config.pageSize,
                url = url,
                translationId = translationId,
                sort = sort,
                search = search,
            )

            when(response) {
                is Resource.Success -> {
                    if (loadType == LoadType.REFRESH) {
                        animeCacheEpisodesDao.clearAllEpisodes()
                        animeCacheEpisodesDao.clearAllTranslations()
                        lastLoadedPage = -1
                    }

                    val episodes = response.data.map { episodeDto ->
                        AnimeCacheEpisodesEntity(
                            number = episodeDto.number,
                            title = episodeDto.title,
                            image = episodeDto.image,
                            aired = episodeDto.aired,
                            description = episodeDto.description,
                            filler = episodeDto.filler,
                            recap = episodeDto.recap,
                        )
                    }

                    val translations = response.data.flatMap { episodeDto ->
                        episodeDto.translations.map { translationDto ->
                            AnimeCacheEpisodesTranslationsEntity(
                                translationId = translationDto.id,
                                episodeNumber = episodeDto.number,
                                link = translationDto.link,
                                translationTitle = translationDto.title
                            )
                        }
                    }
                    animeCacheEpisodesDao.insertEpisodes(episodes)
                    animeCacheEpisodesDao.insertTranslations(translations)

                    lastLoadedPage = loadKey
                    MediatorResult.Success(endOfPaginationReached = episodes.isEmpty())
                }
                is Resource.Error -> {
                    MediatorResult.Error(Exception("Failed to load: ${response.error}"))
                }
                is Resource.Loading -> {
                    MediatorResult.Error(Exception("Unexpected loading state"))
                }
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}