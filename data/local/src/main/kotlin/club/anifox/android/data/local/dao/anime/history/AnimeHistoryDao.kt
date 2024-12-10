package club.anifox.android.data.local.dao.anime.history

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import club.anifox.android.data.local.model.anime.history.AnimeHistoryEntity

@Dao
interface AnimeHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animes: List<AnimeHistoryEntity>)

    @Query("SELECT * FROM anime ORDER BY title ASC")
    fun pagingSource(): PagingSource<Int, AnimeHistoryEntity>

    @Query("DELETE FROM anime")
    suspend fun clearAll()
}