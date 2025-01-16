package club.anifox.android.data.local.dao.anime

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import club.anifox.android.data.local.model.anime.AnimeEntity

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animes: List<AnimeEntity>)

    @Query("SELECT COUNT(*) FROM anime WHERE url = :animeUrl")
    suspend fun doesAnimeExist(animeUrl: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(anime: AnimeEntity)

    @Query("SELECT * FROM anime ORDER BY title ASC")
    fun pagingSource(): PagingSource<Int, AnimeEntity>

    @Query("DELETE FROM anime")
    suspend fun clearAll()
}