package su.anifox.data.local

expect class DataBaseFactory {
    fun createRoomDatabase(): AppDatabase
}