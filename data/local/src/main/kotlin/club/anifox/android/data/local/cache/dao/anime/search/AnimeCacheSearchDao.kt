package club.anifox.android.data.local.cache.dao.anime.search

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import club.anifox.android.data.local.cache.model.anime.search.AnimeCacheSearchEntity

@Dao
interface AnimeCacheSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animes: List<AnimeCacheSearchEntity>)

    @Query("SELECT * FROM cache_anime_search")
    fun pagingSource(): PagingSource<Int, AnimeCacheSearchEntity>

    @Query("DELETE FROM cache_anime_search")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM cache_anime_search")
    suspend fun getCount(): Int
}