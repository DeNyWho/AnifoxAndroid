package club.anifox.android.data.local.dao.anime.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import club.anifox.android.data.local.model.anime.search.AnimeSearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeSearchHistoryDao {
    @Query("SELECT * FROM anime_search_history ORDER BY timestamp DESC LIMIT 10")
    fun getLastSearches(): Flow<List<AnimeSearchHistoryEntity>>

    @Query("SELECT COUNT(*) FROM anime_search_history WHERE `query` = :query")
    suspend fun searchExists(query: String): Int

    @Transaction
    suspend fun insertUniqueSearch(search: AnimeSearchHistoryEntity) {
        val exists = searchExists(search.query)
        if (exists == 0) {
            insertSearch(search)
            keepOnly10LastSearches()
        } else {
            // Update timestamp for existing query
            updateSearchTimestamp(search.query)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: AnimeSearchHistoryEntity)

    @Query("UPDATE anime_search_history SET timestamp = :timestamp WHERE `query` = :query")
    suspend fun updateSearchTimestamp(
        query: String,
        timestamp: Long = System.currentTimeMillis()
    )

    @Query("DELETE FROM anime_search_history WHERE id NOT IN (SELECT id FROM anime_search_history ORDER BY timestamp DESC LIMIT 10)")
    suspend fun keepOnly10LastSearches()

    @Query("DELETE FROM anime_search_history")
    suspend fun deleteSearch()
}