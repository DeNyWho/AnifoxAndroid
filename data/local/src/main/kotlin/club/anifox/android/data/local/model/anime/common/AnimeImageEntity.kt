package club.anifox.android.data.local.model.anime.common

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import club.anifox.android.data.local.model.anime.AnimeEntity

@Entity(tableName = "anime_images")
data class AnimeImageEntity(
    @PrimaryKey val animeUrl: String,
    val large: String,
    val medium: String
)

data class AnimeWithImage(
    @Embedded val anime: AnimeEntity,
    @Relation(
        parentColumn = "url",
        entityColumn = "animeUrl"
    )
    val image: AnimeImageEntity
)