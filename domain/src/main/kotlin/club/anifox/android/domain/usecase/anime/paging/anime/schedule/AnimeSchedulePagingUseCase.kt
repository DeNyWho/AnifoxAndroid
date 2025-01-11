package club.anifox.android.domain.usecase.anime.paging.anime.schedule

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.enum.WeekDay
import club.anifox.android.domain.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class AnimeSchedulePagingUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        dayOfWeek: WeekDay,
        date: LocalDate,
    ): Flow<PagingData<AnimeLight>> {
        return animeRepository.getAnimeScheduleForDayPaged(
            dayOfWeek = dayOfWeek,
            date = date,
        )
    }
}