package club.anifox.android.data.local.cache.dao.anime.episodes

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import club.anifox.android.data.local.cache.model.anime.episodes.AnimeCacheEpisodesEntity
import club.anifox.android.data.local.cache.model.anime.translation.AnimeCacheEpisodesTranslationsEntity

@Dao
interface AnimeCacheEpisodesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: AnimeCacheEpisodesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(translation: AnimeCacheEpisodesTranslationsEntity)

    @Transaction
    @Query("SELECT * FROM cache_anime_episodes WHERE number = :episodeNumber")
    suspend fun getEpisodeWithTranslations(episodeNumber: Int): List<AnimeCacheEpisodeWithTranslations>
}

data class AnimeCacheEpisodeWithTranslations(
    @Embedded val episode: AnimeCacheEpisodesEntity,
    @Relation(
        parentColumn = "number",
        entityColumn = "episodeNumber"
    )
    val translations: List<AnimeCacheEpisodesTranslationsEntity>
)