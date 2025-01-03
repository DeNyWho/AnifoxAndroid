package club.anifox.android.data.local.cache.model.anime.search

import androidx.room.Entity
import androidx.room.PrimaryKey
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType

@Entity(tableName = "cache_anime_search")
data class AnimeCacheSearchEntity(
    @PrimaryKey val url: String,
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