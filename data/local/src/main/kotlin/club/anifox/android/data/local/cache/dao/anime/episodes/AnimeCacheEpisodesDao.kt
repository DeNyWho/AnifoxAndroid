package club.anifox.android.data.local.cache.dao.anime.episodes

import androidx.paging.PagingSource
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
    suspend fun insertEpisodes(episodes: List<AnimeCacheEpisodesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslations(translations: List<AnimeCacheEpisodesTranslationsEntity>)

    @Transaction
    @Query("""
        SELECT * FROM cache_anime_episodes 
        ORDER BY number DESC
    """)
    fun getPagedEpisodes(): PagingSource<Int, AnimeCacheEpisodeWithTranslations>

    @Query("DELETE FROM cache_anime_episodes")
    suspend fun clearAllEpisodes()

    @Query("DELETE FROM cache_anime_episodes_translations")
    suspend fun clearAllTranslations()
}

data class AnimeCacheEpisodeWithTranslationTuple(
    @Embedded val episode: AnimeCacheEpisodesEntity,
    @Embedded val translation: AnimeCacheEpisodesTranslationsEntity
)

data class AnimeCacheEpisodeWithTranslations(
    @Embedded val episode: AnimeCacheEpisodesEntity,
    @Relation(
        parentColumn = "number",
        entityColumn = "episodeNumber"
    )
    val translations: List<AnimeCacheEpisodesTranslationsEntity>
)