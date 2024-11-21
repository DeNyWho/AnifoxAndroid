package club.anifox.android.data.local.model.anime.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_search_history")
data class AnimeSearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val query: String,
    val timestamp: Long = System.currentTimeMillis(),
)