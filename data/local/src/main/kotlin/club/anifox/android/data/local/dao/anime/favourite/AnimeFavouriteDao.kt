package club.anifox.android.data.local.dao.anime.favourite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import club.anifox.android.data.local.model.anime.favourite.AnimeFavouriteEntity
import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeFavouriteDao {
    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT 
            a.title,
            a.image,
            a.url,
            a.type,
            a.rating,
            a.year,
            a.status,
            a.season,
            a.description,
            a.lastWatchedEpisode,
            COALESCE(af.addedAt, CURRENT_TIMESTAMP) as addedAt
        FROM anime a
        LEFT JOIN anime_favourite af ON a.url = af .animeUrl
        WHERE af.isFavourite = 1 OR af.isInHistory = 1 OR af.watchStatus IS NOT NULL
        ORDER BY af.lastUpdatedAt DESC
    """)
    fun getAllAnimeLightFavourites(): Flow<List<AnimeLightFavourite>>

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT 
            a.title,
            a.image,
            a.url,
            a.type,
            a.rating,
            a.year,
            a.status,
            a.season,
            a.description,
            a.lastWatchedEpisode,
            COALESCE(af.addedAt, CURRENT_TIMESTAMP) as addedAt
        FROM anime a
        LEFT JOIN anime_favourite af ON a.url = af.animeUrl
        WHERE af.isFavourite = 1
        ORDER BY af.lastUpdatedAt DESC
    """)
    fun getFavouriteAnime(): Flow<List<AnimeLightFavourite>>

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT 
            a.title,
            a.image,
            a.url,
            a.type,
            a.rating,
            a.year,
            a.status,
            a.season,
            a.description,
            a.lastWatchedEpisode,
            COALESCE(af.addedAt, CURRENT_TIMESTAMP) as addedAt
        FROM anime a
        LEFT JOIN anime_favourite af ON a.url = af.animeUrl
        WHERE af.isInHistory = 1
        ORDER BY af.lastUpdatedAt DESC
    """)
    fun getHistoryAnime(): Flow<List<AnimeLightFavourite>>

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT 
            a.title,
            a.image,
            a.url,
            a.type,
            a.rating,
            a.year,
            a.status,
            a.season,
            a.description,
            a.lastWatchedEpisode,
            COALESCE(af.addedAt, CURRENT_TIMESTAMP) as addedAt
        FROM anime a
        LEFT JOIN anime_favourite af ON a.url = af.animeUrl
        WHERE af.watchStatus = :status
        ORDER BY af.lastUpdatedAt DESC
    """)
    fun getAnimeByStatus(status: AnimeFavouriteStatus): Flow<List<AnimeLightFavourite>>

    @Query("SELECT * FROM anime_favourite WHERE animeUrl = :animeUrl")
    suspend fun getStatusById(animeUrl: String): AnimeFavouriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatus(status: AnimeFavouriteEntity)

}