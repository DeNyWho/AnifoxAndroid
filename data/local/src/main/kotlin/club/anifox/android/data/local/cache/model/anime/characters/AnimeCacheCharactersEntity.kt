package club.anifox.android.data.local.cache.model.anime.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache_anime_characters")
data class AnimeCacheCharactersEntity(
    @PrimaryKey
    val id: String,
    val role: String,
    val image: String,
    val name: String,
)
