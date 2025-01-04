package club.anifox.android.data.local.cache.model.anime.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache_anime_characters_available_roles")
data class AnimeCacheCharactersAvailableRolesEntity(
    @PrimaryKey
    val animeUrl: String,
    val roles: String,
)