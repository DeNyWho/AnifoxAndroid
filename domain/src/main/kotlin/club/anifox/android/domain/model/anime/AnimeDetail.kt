package club.anifox.android.domain.model.anime

import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.image.AnimeImage
import java.time.LocalDate
import java.time.LocalDateTime

data class AnimeDetail(
    val url: String,
    val title: String,
    val image: AnimeImage,
    val type: AnimeType,
    val rating: Double?,
    val ratingMpa: String?,
    val minimalAge: Int,
    val year: Int,
    val status: AnimeStatus,
    val season: AnimeSeason,
    val episodes: Int?,
    val episodesAired: Int,
    val nextEpisode: LocalDateTime?,
//    val genres: List<AnimeGenre>,
//    val studios: List<AnimeStudio>,
    val releasedOn: LocalDate?,
    val airedOn: LocalDate,
    val description: String?,
//    val translations: List<AnimeTranslations>,
)
