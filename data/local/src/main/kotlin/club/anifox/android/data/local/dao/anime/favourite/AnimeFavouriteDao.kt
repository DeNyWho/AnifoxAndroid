package club.anifox.android.data.local.dao.anime.favourite

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.local.model.anime.favourite.AnimeFavouriteEntity
import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus

@Dao
interface AnimeFavouriteDao {

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT 
            a.title,
            ai.medium as image,
            a.url,
            a.type,
            a.rating,
            a.year,
            a.status,
            a.season,
            a.description,
            a.lastWatchedEpisode,
            COALESCE(af.addedAt, CURRENT_TIMESTAMP) as addedAt,
            a.episodes,
            a.episodesAired
        FROM anime a
        LEFT JOIN anime_favourite af ON a.url = af.animeUrl
        LEFT JOIN anime_images ai ON a.url = ai.animeUrl
        WHERE af.isFavourite = 1
        ORDER BY af.lastUpdatedAt DESC
    """)
    fun getFavouriteAnimePaging(): PagingSource<Int, AnimeLightFavourite>

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT 
            a.title,
            ai.medium as image,
            a.url,
            a.type,
            a.rating,
            a.year,
            a.status,
            a.season,
            a.description,
            a.lastWatchedEpisode,
            COALESCE(af.addedAt, CURRENT_TIMESTAMP) as addedAt,
            a.episodes,
            a.episodesAired
        FROM anime a
        LEFT JOIN anime_favourite af ON a.url = af.animeUrl
        LEFT JOIN anime_images ai ON a.url = ai.animeUrl
        WHERE af.isInHistory = 1
        ORDER BY af.lastUpdatedAt DESC
    """)
    fun getHistoryAnimePaging(): PagingSource<Int, AnimeLightFavourite>

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT 
            a.title,
            ai.medium as image,
            a.url,
            a.type,
            a.rating,
            a.year,
            a.status,
            a.season,
            a.description,
            a.lastWatchedEpisode,
            COALESCE(af.addedAt, CURRENT_TIMESTAMP) as addedAt,
            a.episodes,
            a.episodesAired
        FROM anime a
        LEFT JOIN anime_favourite af ON a.url = af.animeUrl
        LEFT JOIN anime_images ai ON a.url = ai.animeUrl
        WHERE af.watchStatus = :status
        ORDER BY af.lastUpdatedAt DESC
    """)
    fun getAnimeByStatusPaging(status: AnimeFavouriteStatus): PagingSource<Int, AnimeLightFavourite>

    @Query("""
        SELECT EXISTS(
            SELECT 1 
            FROM anime_favourite 
            WHERE animeUrl = :animeUrl AND isFavourite = 1
        )
    """)
    suspend fun isAnimeInFavourite(animeUrl: String): Boolean

    @Query("""
        SELECT watchStatus
        FROM anime_favourite
        WHERE animeUrl = :animeUrl
    """)
    suspend fun getAnimeStatus(animeUrl: String): AnimeFavouriteStatus?

    @Query("SELECT * FROM anime_favourite WHERE animeUrl = :animeUrl")
    suspend fun getStatusById(animeUrl: String): AnimeFavouriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatus(status: AnimeFavouriteEntity)

    @Transaction
    suspend fun insertStatusIfAnimeExists(animeUrl: String, status: AnimeFavouriteEntity, animeDao: AnimeDao) {
        val animeExists = animeDao.doesAnimeExist(animeUrl) > 0
        if (animeExists) {
            insertStatus(status)
        } else {
            throw IllegalArgumentException("Anime with url $animeUrl does not exist")
        }
    }
}