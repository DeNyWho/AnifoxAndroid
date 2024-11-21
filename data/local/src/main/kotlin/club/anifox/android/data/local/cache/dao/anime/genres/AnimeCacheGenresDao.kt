package club.anifox.android.data.local.cache.dao.anime.genres

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import club.anifox.android.data.local.cache.model.anime.genres.AnimeCacheGenresEntity

@Dao
interface AnimeCacheGenresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<AnimeCacheGenresEntity>)

    @Query("SELECT * FROM cache_anime_genres")
    fun pagingSource(): PagingSource<Int, AnimeCacheGenresEntity>

    @Query("DELETE FROM cache_anime_genres")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM cache_anime_genres")
    suspend fun getCount(): Int
}