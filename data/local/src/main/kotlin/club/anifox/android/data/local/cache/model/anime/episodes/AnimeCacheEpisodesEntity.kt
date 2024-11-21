package club.anifox.android.data.local.cache.model.anime.episodes

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "cache_anime_episodes")
data class AnimeCacheEpisodesEntity (
    @PrimaryKey val number: Int,
    val title: String,
    val image: String,
    val aired: LocalDate,
    val filler: Boolean,
    val recap: Boolean,
)
