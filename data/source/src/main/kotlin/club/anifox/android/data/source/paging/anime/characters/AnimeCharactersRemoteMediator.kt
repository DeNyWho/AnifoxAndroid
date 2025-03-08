package club.anifox.android.data.source.paging.anime.characters

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import club.anifox.android.data.local.cache.dao.anime.characters.AnimeCacheCharactersAvailableRolesDao
import club.anifox.android.data.local.cache.dao.anime.characters.AnimeCacheCharactersDao
import club.anifox.android.data.local.cache.model.anime.characters.AnimeCacheCharactersAvailableRolesEntity
import club.anifox.android.data.local.cache.model.anime.characters.AnimeCacheCharactersEntity
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.mapper.anime.toEntity
import club.anifox.android.domain.model.common.request.Resource

@OptIn(ExperimentalPagingApi::class)
internal class AnimeCharactersRemoteMediator(
    private val animeService: AnimeService,
    private val animeCacheCharactersDao: AnimeCacheCharactersDao,
    private val animeCacheCharactersAvailableRolesDao: AnimeCacheCharactersAvailableRolesDao,
    private val url: String,
    private val search: String,
) : RemoteMediator<Int, AnimeCacheCharactersEntity>() {

    private var lastLoadedPage = -1
    private var currentParams: Params = Params(search)

    private data class Params(val search: String)

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeCacheCharactersEntity>
    ): MediatorResult {
        val newParams = Params(search)
        if (newParams != currentParams) {
            currentParams = newParams
            lastLoadedPage = -1
            animeCacheCharactersDao.clearAll()
        }

        return try {
            if (newParams != currentParams) {
                currentParams = newParams
                return load(LoadType.REFRESH, state)
            }

            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    animeCacheCharactersDao.clearAll()
                    lastLoadedPage = -1
                    0
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> lastLoadedPage + 1
            }

            val response = animeService.getAnimeCharacters(
                page = loadKey,
                limit = state.config.pageSize,
                url = url,
                search = currentParams.search,
            )

            when (response) {
                is Resource.Success -> {
                    val characters = response.data.characters.map { it.toEntity() }
                    val availableRoles = response.data.availableRoles

                    if (loadType == LoadType.REFRESH) {
                        animeCacheCharactersDao.clearAll()
                        animeCacheCharactersAvailableRolesDao.clearAll()
                        lastLoadedPage = -1
                    }

                    animeCacheCharactersDao.insertAll(characters)
                    animeCacheCharactersAvailableRolesDao.insert(
                        AnimeCacheCharactersAvailableRolesEntity(
                            animeUrl = url,
                            roles = availableRoles.joinToString(",") // Serializing the list into a string
                        )
                    )

                    lastLoadedPage = loadKey
                    MediatorResult.Success(endOfPaginationReached = characters.isEmpty())
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