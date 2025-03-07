package club.anifox.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import club.anifox.android.data.local.cache.dao.anime.catalog.AnimeCacheCatalogDao
import club.anifox.android.data.local.cache.dao.anime.characters.AnimeCacheCharactersAvailableRolesDao
import club.anifox.android.data.local.cache.dao.anime.characters.AnimeCacheCharactersDao
import club.anifox.android.data.local.cache.dao.anime.episodes.AnimeCacheEpisodesDao
import club.anifox.android.data.local.cache.dao.anime.genres.AnimeCacheGenresDao
import club.anifox.android.data.local.cache.dao.anime.schedule.AnimeCacheScheduleDao
import club.anifox.android.data.local.cache.dao.anime.search.AnimeCacheSearchDao
import club.anifox.android.data.local.cache.model.anime.catalog.AnimeCacheCatalogEntity
import club.anifox.android.data.local.cache.model.anime.characters.AnimeCacheCharactersAvailableRolesEntity
import club.anifox.android.data.local.cache.model.anime.characters.AnimeCacheCharactersEntity
import club.anifox.android.data.local.cache.model.anime.episodes.AnimeCacheEpisodesEntity
import club.anifox.android.data.local.cache.model.anime.genres.AnimeCacheGenresEntity
import club.anifox.android.data.local.cache.model.anime.schedule.AnimeCacheScheduleEntity
import club.anifox.android.data.local.cache.model.anime.search.AnimeCacheSearchEntity
import club.anifox.android.data.local.cache.model.anime.translation.AnimeCacheEpisodesTranslationsEntity
import club.anifox.android.data.local.converters.AnimeConverters
import club.anifox.android.data.local.converters.LocalDateConverter
import club.anifox.android.data.local.converters.LocalDateTimeConverter
import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.local.dao.anime.common.AnimeImageDao
import club.anifox.android.data.local.dao.anime.favourite.AnimeFavouriteDao
import club.anifox.android.data.local.dao.anime.search.AnimeSearchHistoryDao
import club.anifox.android.data.local.model.anime.AnimeEntity
import club.anifox.android.data.local.model.anime.common.AnimeImageEntity
import club.anifox.android.data.local.model.anime.favourite.AnimeFavouriteEntity
import club.anifox.android.data.local.model.anime.search.AnimeSearchHistoryEntity

@Database(
    entities = [
        AnimeEntity::class,
        AnimeImageEntity::class,
        AnimeFavouriteEntity::class,
        AnimeSearchHistoryEntity::class,
        AnimeCacheSearchEntity::class,
        AnimeCacheGenresEntity::class,
        AnimeCacheCatalogEntity::class,
        AnimeCacheEpisodesEntity::class,
        AnimeCacheScheduleEntity::class,
        AnimeCacheCharactersEntity::class,
        AnimeCacheCharactersAvailableRolesEntity::class,
        AnimeCacheEpisodesTranslationsEntity::class
    ],
    version = 20,
    exportSchema = true,
)
@TypeConverters(LocalDateConverter::class, LocalDateTimeConverter::class, AnimeConverters::class)
internal abstract class AnifoxDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun animeImageDao(): AnimeImageDao
    abstract fun animeFavouriteStatusDao(): AnimeFavouriteDao
    abstract fun animeSearchHistoryDao(): AnimeSearchHistoryDao
    abstract fun animeCacheSearchDao(): AnimeCacheSearchDao
    abstract fun animeCacheGenresDao(): AnimeCacheGenresDao
    abstract fun animeCacheCatalogDao(): AnimeCacheCatalogDao
    abstract fun animeCacheEpisodesDao(): AnimeCacheEpisodesDao
    abstract fun animeCacheScheduleDao(): AnimeCacheScheduleDao
    abstract fun animeCacheCharactersDao(): AnimeCacheCharactersDao
    abstract fun animeCacheCharactersAvailableRolesDao(): AnimeCacheCharactersAvailableRolesDao
}