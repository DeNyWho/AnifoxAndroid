package club.anifox.android.data.local.model.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType

@Entity(tableName = "anime")
data class AnimeEntity(
    @PrimaryKey val url: String,
    val title: String,
    val image: String,
    val type: AnimeType,
    val rating: Double?,
    val year: Int,
    val status: AnimeStatus,
    val season: AnimeSeason,
    val lastWatchedEpisode: Int = 0,
)