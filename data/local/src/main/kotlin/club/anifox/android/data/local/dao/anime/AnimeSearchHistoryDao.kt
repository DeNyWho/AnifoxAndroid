package club.anifox.android.data.local.dao.anime

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import club.anifox.android.data.local.model.anime.AnimeSearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeSearchHistoryDao {
    @Query("SELECT * FROM anime_search_history ORDER BY timestamp DESC LIMIT 10")
    fun getLastSearches(): Flow<List<AnimeSearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: AnimeSearchHistoryEntity)

    @Query("DELETE FROM anime_search_history WHERE id NOT IN (SELECT id FROM anime_search_history ORDER BY timestamp DESC LIMIT 10)")
    suspend fun keepOnly10LastSearches()

    @Query("DELETE FROM anime_search_history")
    suspend fun deleteSearch()
}