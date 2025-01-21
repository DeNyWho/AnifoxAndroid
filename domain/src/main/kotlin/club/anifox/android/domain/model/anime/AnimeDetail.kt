package club.anifox.android.domain.model.anime

import androidx.compose.runtime.Immutable
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.image.AnimeImage
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalDateTime

@Immutable
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
    val titleOther: ImmutableList<String>? = null,
    val titleEnglish: ImmutableList<String>? = null,
    val titleJapan: ImmutableList<String>? = null,
    val synonyms: ImmutableList<String> = persistentListOf(),
    val releasedOn: LocalDate? = null,
    val airedOn: LocalDate = LocalDate.now(),
    val description: String? = null,
    val genres: ImmutableList<AnimeGenre> = persistentListOf(),
    val studios: ImmutableList<AnimeStudio> = persistentListOf(),
//    val translations: List<AnimeTranslations>,
)
