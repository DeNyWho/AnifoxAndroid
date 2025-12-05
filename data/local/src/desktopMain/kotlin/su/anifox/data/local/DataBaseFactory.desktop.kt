package su.anifox.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import java.io.File

actual class DataBaseFactory {
    actual fun createRoomDatabase(): AppDatabase {
        val dbFile = File(System.getProperty("java.io.tmpdir"), DB_FILE_NAME)
        return Room
            .databaseBuilder<AppDatabase>(
                name = dbFile.absolutePath,
            )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}