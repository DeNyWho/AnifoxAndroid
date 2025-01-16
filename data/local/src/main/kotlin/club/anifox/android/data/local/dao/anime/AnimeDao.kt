package club.anifox.android.data.local.dao.anime

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import club.anifox.android.data.local.model.anime.AnimeEntity
import club.anifox.android.data.local.model.anime.common.AnimeImageEntity

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animes: List<AnimeEntity>)

    @Query("SELECT COUNT(*) FROM anime WHERE url = :animeUrl")
    suspend fun doesAnimeExist(animeUrl: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(anime: AnimeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: AnimeImageEntity)

    @Transaction
    suspend fun insertAnimeWithImage(anime: AnimeEntity, image: AnimeImageEntity) {
        insert(anime)
        insertImage(image)
    }

    @Query("SELECT * FROM anime ORDER BY title ASC")
    fun pagingSource(): PagingSource<Int, AnimeEntity>

    @Query("DELETE FROM anime")
    suspend fun clearAll()

    @Query("DELETE FROM anime_images")
    suspend fun clearAllImages()

    @Transaction
    suspend fun clearAllData() {
        clearAll()
        clearAllImages()
    }
}