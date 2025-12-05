package su.anifox.data.local.model.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "anime")
data class AnimeEntity(
    @PrimaryKey val url: String,
    val title: String,
    val rating: Double? = null,
    val ratingMpa: String? = null,
    val minimalAge: Int = 0,
    val year: Int = 0,
    val description: String = "",
    val lastWatchedEpisode: Int = 0,
    val episodes: Int? = null,
    val episodesAired: Int = 0,
    val nextEpisode: LocalDateTime? = null,
    val releasedOn: LocalDate? = null,
    val airedOn: LocalDate = LocalDate.now(),
    val genres: String,
    val studios: String,
)