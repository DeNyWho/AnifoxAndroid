package club.anifox.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import club.anifox.android.data.local.cache.dao.anime.catalog.AnimeCacheCatalogDao
import club.anifox.android.data.local.cache.dao.anime.episodes.AnimeCacheEpisodesDao
import club.anifox.android.data.local.cache.dao.anime.genres.AnimeCacheGenresDao
import club.anifox.android.data.local.cache.dao.anime.search.AnimeCacheSearchDao
import club.anifox.android.data.local.cache.model.anime.catalog.AnimeCacheCatalogEntity
import club.anifox.android.data.local.cache.model.anime.episodes.AnimeCacheEpisodesEntity
import club.anifox.android.data.local.cache.model.anime.genres.AnimeCacheGenresEntity
import club.anifox.android.data.local.cache.model.anime.search.AnimeCacheSearchEntity
import club.anifox.android.data.local.cache.model.anime.translation.AnimeCacheEpisodesTranslationsEntity
import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.local.model.anime.AnimeEntity

@Database(
    entities = [
        AnimeEntity::class,
        AnimeCacheSearchEntity::class,
        AnimeCacheGenresEntity::class,
        AnimeCacheCatalogEntity::class,
        AnimeCacheEpisodesEntity::class,
        AnimeCacheEpisodesTranslationsEntity::class
               ],
    version = 6,
    exportSchema = true,
)
internal abstract class AnifoxDatabase: RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun animeCacheSearchDao(): AnimeCacheSearchDao
    abstract fun animeCacheGenresDao(): AnimeCacheGenresDao
    abstract fun animeCacheCatalogDao(): AnimeCacheCatalogDao
    abstract fun animeCacheEpisodesDao(): AnimeCacheEpisodesDao
}