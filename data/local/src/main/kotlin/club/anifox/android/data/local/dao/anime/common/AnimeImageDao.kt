package club.anifox.android.data.local.dao.anime.common

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import club.anifox.android.data.local.model.anime.common.AnimeImageEntity

@Dao
interface AnimeImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(animeImageEntity: AnimeImageEntity)

    @Query("SELECT * FROM anime_images WHERE animeUrl = :url")
    suspend fun getImageByUrl(url: String): AnimeImageEntity?
}