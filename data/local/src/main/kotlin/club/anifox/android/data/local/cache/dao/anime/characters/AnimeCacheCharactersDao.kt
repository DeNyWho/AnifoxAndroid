package club.anifox.android.data.local.cache.dao.anime.characters

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import club.anifox.android.data.local.cache.model.anime.characters.AnimeCacheCharactersEntity

@Dao
interface AnimeCacheCharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<AnimeCacheCharactersEntity>)

    @Query("SELECT * FROM cache_anime_characters")
    fun pagingSource(): PagingSource<Int, AnimeCacheCharactersEntity>

    @Query("DELETE FROM cache_anime_characters")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM cache_anime_characters")
    suspend fun getCount(): Int
}