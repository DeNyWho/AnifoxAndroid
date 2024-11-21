package club.anifox.android.data.source.repository.anime.favourite

import club.anifox.android.data.local.dao.anime.favourite.AnimeFavouriteDao
import club.anifox.android.domain.repository.anime.AnimeFavouriteRepository
import javax.inject.Inject

internal class AnimeFavouriteImpl @Inject constructor(
    private val animeFavouriteDao: AnimeFavouriteDao,
) : AnimeFavouriteRepository {

}