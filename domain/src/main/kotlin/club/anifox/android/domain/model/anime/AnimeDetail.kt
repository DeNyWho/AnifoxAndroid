package club.anifox.android.domain.model.anime

import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.image.AnimeImage
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import java.time.LocalDate
import java.time.LocalDateTime

data class AnimeDetail(
    val url: String = "",
    val title: String = "",
    val image: AnimeImage = AnimeImage(),
    val type: AnimeType = AnimeType.Tv,
    val rating: Double? = null,
    val ratingMpa: String? = null,
    val minimalAge: Int = 0,
    val year: Int = 0,
    val status: AnimeStatus = AnimeStatus.Ongoing,
    val season: AnimeSeason = AnimeSeason.Fall,
    val episodes: Int? = null,
    val episodesAired: Int = 0,
    val nextEpisode: LocalDateTime? = null,
    val titleOther: List<String>? = null,
    val titleEnglish: List<String>? = null,
    val titleJapan: List<String>? = null,
    val synonyms: List<String> = listOf(),
    val releasedOn: LocalDate? = null,
    val airedOn: LocalDate = LocalDate.now(),
    val description: String? = null,
    val genre: List<AnimeGenre> = listOf(),
    val studio: List<AnimeStudio> = listOf(),
//    val translations: List<AnimeTranslations>,
)
