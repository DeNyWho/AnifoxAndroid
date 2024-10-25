package club.anifox.android.data.local.cache.model.anime.translation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import club.anifox.android.data.local.cache.model.anime.episodes.AnimeCacheEpisodesEntity

@Entity(
    tableName = "cache_anime_episodes_translations",
    foreignKeys = [ForeignKey(
        entity = AnimeCacheEpisodesEntity::class,
        parentColumns = ["number"],
        childColumns = ["episodeNumber"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AnimeCacheEpisodesTranslationsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val translationId: Int = 0,
    @ColumnInfo(name = "episodeNumber")
    val episodeNumber: Int,
    val link: String,
    val title: String
)