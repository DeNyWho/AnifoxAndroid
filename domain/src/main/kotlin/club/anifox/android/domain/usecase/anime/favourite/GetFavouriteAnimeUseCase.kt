package club.anifox.android.domain.usecase.anime.favourite

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.domain.model.anime.enum.AnimeListType
import club.anifox.android.domain.repository.anime.AnimeFavouriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteAnimeUseCase(
    private val animeFavouriteRepository: AnimeFavouriteRepository
) {
    operator fun invoke(type: AnimeListType): Flow<PagingData<AnimeLightFavourite>> = when (type) {
        is AnimeListType.History -> animeFavouriteRepository.getHistoryAnime()
        is AnimeListType.Favourite -> animeFavouriteRepository.getFavouriteAnime()
        is AnimeListType.Status -> animeFavouriteRepository.getAnimeByStatus(type.status)
    }
}
