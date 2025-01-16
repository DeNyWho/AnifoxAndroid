package club.anifox.android.domain.repository.anime

import club.anifox.android.domain.model.anime.AnimeDetail

interface AnimeLocalRepository {
    suspend fun insertAnimeDetail(anime: AnimeDetail)
    suspend fun checkAnimeUrl(url: String): Boolean
}