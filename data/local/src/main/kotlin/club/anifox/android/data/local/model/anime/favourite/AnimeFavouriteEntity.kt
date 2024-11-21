package club.anifox.android.data.local.model.anime.favourite

import androidx.room.Entity
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import java.time.LocalDate

@Entity(
    tableName = "anime_favourite",
    primaryKeys = ["animeId"],
)
data class AnimeFavouriteEntity(
    val animeId: Int,
    val isFavourite: Boolean = false,
    val isInHistory: Boolean = false,
    val status: AnimeFavouriteStatus? = null,
    val addedAt: LocalDate = LocalDate.now(),
)