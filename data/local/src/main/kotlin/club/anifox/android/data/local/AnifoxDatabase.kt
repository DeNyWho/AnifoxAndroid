package club.anifox.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.local.model.anime.AnimeEntity

@Database(
    entities = [AnimeEntity::class],
    version = 1,
    exportSchema = true
)
internal abstract class AnifoxDatabase: RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}