package club.anifox.android.data.local.cache.dao.anime.catalog

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import club.anifox.android.data.local.cache.model.anime.catalog.AnimeCacheCatalogEntity

@Dao
interface AnimeCacheCatalogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<AnimeCacheCatalogEntity>)

    @Query("SELECT * FROM cache_anime_catalog")
    fun pagingSource(): PagingSource<Int, AnimeCacheCatalogEntity>

    @Query("DELETE FROM cache_anime_catalog")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM cache_anime_catalog")
    suspend fun getCount(): Int
}