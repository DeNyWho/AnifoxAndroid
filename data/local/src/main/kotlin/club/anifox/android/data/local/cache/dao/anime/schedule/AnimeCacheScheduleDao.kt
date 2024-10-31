package club.anifox.android.data.local.cache.dao.anime.schedule

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import club.anifox.android.data.local.cache.model.anime.schedule.AnimeCacheScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeCacheScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeSchedules(schedules: List<AnimeCacheScheduleEntity>)

    @Query("SELECT * FROM cache_anime_schedule WHERE dayOfWeek = :dayOfWeek")
    fun getAnimeSchedulesForDay(dayOfWeek: String): Flow<List<AnimeCacheScheduleEntity>>

    @Query("DELETE FROM cache_anime_schedule WHERE dayOfWeek = :dayOfWeek")
    suspend fun deleteSchedulesForDay(dayOfWeek: String)
}