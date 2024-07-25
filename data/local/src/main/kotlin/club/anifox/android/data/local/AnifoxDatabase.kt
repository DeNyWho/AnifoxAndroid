package club.anifox.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import club.anifox.android.data.local.cache.dao.anime.search.AnimeSearchDao
import club.anifox.android.data.local.cache.model.anime.search.AnimeSearchEntity
import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.local.model.anime.AnimeEntity

@Database(
    entities = [AnimeEntity::class, AnimeSearchEntity::class],
    version = 2,
    exportSchema = true,
)
internal abstract class AnifoxDatabase: RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun animeSearchDao(): AnimeSearchDao
}