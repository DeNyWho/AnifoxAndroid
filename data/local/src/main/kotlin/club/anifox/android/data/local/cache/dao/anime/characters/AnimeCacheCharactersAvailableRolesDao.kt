package club.anifox.android.data.local.cache.dao.anime.characters

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import club.anifox.android.data.local.cache.model.anime.characters.AnimeCacheCharactersAvailableRolesEntity

@Dao
interface AnimeCacheCharactersAvailableRolesDao {

    @Query("SELECT * FROM cache_anime_characters_available_roles WHERE animeUrl = :animeUrl")
    suspend fun getByAnimeUrl(animeUrl: String): AnimeCacheCharactersAvailableRolesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: AnimeCacheCharactersAvailableRolesEntity)

    @Query("DELETE FROM cache_anime_characters_available_roles")
    suspend fun clearAll()
}