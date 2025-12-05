package su.anifox.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import su.anifox.data.local.converters.LocalDateConverter
import su.anifox.data.local.converters.LocalDateTimeConverter
import su.anifox.data.local.model.anime.AnimeEntity

@Database(entities = [AnimeEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(LocalDateConverter::class, LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {

}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

internal const val DB_FILE_NAME = "anifox.db"