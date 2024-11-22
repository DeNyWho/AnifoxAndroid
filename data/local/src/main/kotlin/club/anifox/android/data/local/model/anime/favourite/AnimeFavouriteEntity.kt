package club.anifox.android.data.local.model.anime.favourite

import androidx.room.Entity
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import java.time.LocalDateTime

@Entity(
    tableName = "anime_favourite",
    primaryKeys = ["animeUrl"],
)
data class AnimeFavouriteEntity(
    val animeUrl: String,
    val isFavourite: Boolean = false,
    val isInHistory: Boolean = false,
    val watchStatus: AnimeFavouriteStatus? = null,
    val addedAt: LocalDateTime = LocalDateTime.now(),
    val lastUpdatedAt: LocalDateTime = LocalDateTime.now()
)
