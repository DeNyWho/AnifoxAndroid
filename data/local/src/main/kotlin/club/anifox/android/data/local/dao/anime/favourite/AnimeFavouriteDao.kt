package club.anifox.android.data.local.dao.anime.favourite

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import club.anifox.android.data.local.model.anime.favourite.AnimeFavouriteEntity

@Dao
interface AnimeFavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(animeFavourite: AnimeFavouriteEntity)

    @Delete
    suspend fun deleteFavourite(animeFavourite: AnimeFavouriteEntity)

    @Update
    suspend fun updateFavourite(favourite: AnimeFavouriteEntity)

    @Query("SELECT * FROM anime_favourite ORDER BY animeId ASC")
    fun getPagedFavourites(): PagingSource<Int, AnimeFavouriteEntity>
}