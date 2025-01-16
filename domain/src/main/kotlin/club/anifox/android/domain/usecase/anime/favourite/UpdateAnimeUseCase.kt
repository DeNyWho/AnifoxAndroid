package club.anifox.android.domain.usecase.anime.favourite

import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import club.anifox.android.domain.repository.anime.AnimeFavouriteRepository

class UpdateAnimeUseCase(
    private val animeFavouriteRepository: AnimeFavouriteRepository
) {
    suspend fun updateAnimeStatus(url: String, status: AnimeFavouriteStatus?) {
        animeFavouriteRepository.updateAnimeStatus(url, status)
    }

    suspend fun updateAnimeFavourite(url: String, isFavourite: Boolean) {
        animeFavouriteRepository.updateAnimeFavourite(url, isFavourite)
    }

    suspend fun updateAnimeHistory(url: String, isInHistory: Boolean) {
        animeFavouriteRepository.updateAnimeHistory(url, isInHistory)
    }
}