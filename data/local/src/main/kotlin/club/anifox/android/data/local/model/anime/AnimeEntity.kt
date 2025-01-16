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
    val image: String,
    val type: AnimeType,
    val rating: Double?,
    val ratingMpa: String?,
    val minimalAge: Int,
    val year: Int,
    val status: AnimeStatus,
    val season: AnimeSeason,
    val description: String,
    val lastWatchedEpisode: Int? = null,
    val episodes: Int?,
    val episodesAired: Int,
    val nextEpisode: LocalDateTime?,
    val titleOther: List<String>?,
    val titleEnglish: List<String>?,
    val titleJapan: List<String>?,
    val synonyms: List<String> = listOf(),
    val releasedOn: LocalDate? = null,
    val airedOn: LocalDate = LocalDate.now(),
    val genres: String = "",
    val studios: String = "",
)