package club.anifox.android.data.source.repository.anime.local

import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.local.mappers.anime.toEntities
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.repository.anime.AnimeLocalRepository
import javax.inject.Inject

internal class AnimeLocalRepositoryImpl @Inject constructor(
    private val animeDao: AnimeDao,
): AnimeLocalRepository {

    override suspend fun insertAnimeDetail(anime: AnimeDetail) {
        val (animeEntity, imageEntity) = anime.toEntities()
        animeDao.insertAnimeWithImage(animeEntity, imageEntity)
    }

    override suspend fun checkAnimeUrl(url: String): Boolean {
        return animeDao.doesAnimeExist(url) > 0
    }
}