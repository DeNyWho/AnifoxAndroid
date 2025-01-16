package club.anifox.android.domain.repository.anime

import club.anifox.android.domain.model.anime.AnimeDetail
import kotlinx.coroutines.flow.Flow

interface AnimeLocalRepository {
    suspend fun insertAnimeDetail(anime: AnimeDetail)
    suspend fun checkAnimeUrl(url: String): Boolean
    suspend fun observeAnimeExists(url: String): Flow<Boolean>
}