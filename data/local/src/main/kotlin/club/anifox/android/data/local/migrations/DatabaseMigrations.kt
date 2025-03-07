package club.anifox.android.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object DatabaseMigrations {
    class Schema19To20 : Migration(19, 20) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS _new_cache_anime_episodes (
                    number INTEGER PRIMARY KEY NOT NULL,
                    title TEXT NOT NULL,
                    image TEXT NOT NULL,
                    aired INTEGER,
                    description TEXT NOT NULL DEFAULT '',
                    filler INTEGER NOT NULL,
                    recap INTEGER NOT NULL
                )
            """
            )

            db.execSQL(
                """
                INSERT INTO _new_cache_anime_episodes (
                    number, title, image, aired, description, filler, recap
                )
                SELECT number, title, image, aired, '' as description, filler, recap
                FROM cache_anime_episodes
            """
            )

            db.execSQL("DROP TABLE cache_anime_episodes")

            db.execSQL("ALTER TABLE _new_cache_anime_episodes RENAME TO cache_anime_episodes")
        }
    }
}