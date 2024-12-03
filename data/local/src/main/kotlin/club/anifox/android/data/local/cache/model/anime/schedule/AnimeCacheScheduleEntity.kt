package club.anifox.android.data.local.cache.model.anime.schedule

import androidx.room.Entity
import androidx.room.PrimaryKey
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.common.enum.WeekDay

@Entity(tableName = "cache_anime_schedule")
data class AnimeCacheScheduleEntity(
    @PrimaryKey val url: String,
    val dayOfWeek: WeekDay,
    val title: String,
    val image: String,
    val type: AnimeType,
    val rating: Double?,
    val year: Int,
    val status: AnimeStatus,
    val season: AnimeSeason,
    val description: String,
    val episodes: Int,
    val episodesAired: Int,
)
