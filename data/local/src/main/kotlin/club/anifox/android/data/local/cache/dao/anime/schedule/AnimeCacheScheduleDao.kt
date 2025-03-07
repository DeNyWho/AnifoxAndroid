package club.anifox.android.data.local.cache.dao.anime.schedule

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import club.anifox.android.data.local.cache.model.anime.schedule.AnimeCacheScheduleEntity

@Dao
interface AnimeCacheScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeSchedules(schedules: List<AnimeCacheScheduleEntity>)

    @Query(
        """
        SELECT * FROM cache_anime_schedule 
        WHERE dayOfWeek = :dayOfWeek 
        ORDER BY title ASC
    """
    )
    fun getPagedAnimeForDay(dayOfWeek: String): PagingSource<Int, AnimeCacheScheduleEntity>

    @Query("SELECT COUNT(*) FROM cache_anime_schedule WHERE dayOfWeek = :dayOfWeek")
    suspend fun getAnimeCountForDay(dayOfWeek: String): Int

    @Query("DELETE FROM cache_anime_schedule WHERE dayOfWeek = :dayOfWeek")
    suspend fun deleteSchedulesForDay(dayOfWeek: String)

    @Query(
        """
        DELETE FROM cache_anime_schedule 
        WHERE url IN (
            SELECT url FROM cache_anime_schedule 
            WHERE dayOfWeek = :dayOfWeek 
            ORDER BY title ASC
            LIMIT :count
        )
    """
    )
    suspend fun deleteOldestSchedulesForDay(dayOfWeek: String, count: Int)

    @Query(
        """
        DELETE FROM cache_anime_schedule 
        WHERE dayOfWeek = :dayOfWeek 
        AND url NOT IN (
            SELECT url FROM cache_anime_schedule 
            WHERE dayOfWeek = :dayOfWeek 
            ORDER BY title ASC 
            LIMIT :limit
        )
    """
    )
    suspend fun limitCacheSize(dayOfWeek: String, limit: Int)
}