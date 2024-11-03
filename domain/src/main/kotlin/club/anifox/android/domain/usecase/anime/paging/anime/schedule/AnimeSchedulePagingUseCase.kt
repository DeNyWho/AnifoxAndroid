package club.anifox.android.domain.usecase.anime.paging.anime.schedule

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.enum.WeekDay
import club.anifox.android.domain.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.Flow

class AnimeSchedulePagingUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        limit: Int = 20,
        dayOfWeek: WeekDay,
    ): Flow<PagingData<AnimeLight>> {
        return animeRepository.getAnimeScheduleForDayPaged(
            limit = limit,
            dayOfWeek = dayOfWeek,
        )
    }
}