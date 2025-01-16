package club.anifox.android.domain.usecase.anime.favourite

import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import club.anifox.android.domain.repository.anime.AnimeFavouriteRepository

class CheckAnimeFavouriteUseCase(
    private val animeFavouriteRepository: AnimeFavouriteRepository
) {
    suspend fun isAnimeInFavourite(url: String): Boolean =
        animeFavouriteRepository.isAnimeInFavourite(url)

    suspend fun getAnimeStatus(url: String): AnimeFavouriteStatus? =
        animeFavouriteRepository.getAnimeStatus(url)
}