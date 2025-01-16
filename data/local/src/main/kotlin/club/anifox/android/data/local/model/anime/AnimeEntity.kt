package club.anifox.android.data.local.model.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "anime")
data class AnimeEntity(
    @PrimaryKey val url: String,
    val title: String,
    val type: AnimeType = AnimeType.Tv,
    val rating: Double? = null,
    val ratingMpa: String? = null,
    val minimalAge: Int = 0,
    val year: Int = 0,
    val status: AnimeStatus = AnimeStatus.Ongoing,
    val season: AnimeSeason = AnimeSeason.Fall,
    val description: String = "",
    val lastWatchedEpisode: Int = 0,
    val episodes: Int? = null,
    val episodesAired: Int = 0,
    val nextEpisode: LocalDateTime? = null,
    val titleOther: List<String>? = null,
    val titleEnglish: List<String>? = null,
    val titleJapan: List<String>? = null,
    val synonyms: List<String> = listOf(),
    val releasedOn: LocalDate? = null,
    val airedOn: LocalDate = LocalDate.now(),
    val genres: String,
    val studios: String,
)