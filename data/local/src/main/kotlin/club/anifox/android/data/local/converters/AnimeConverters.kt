package club.anifox.android.data.local.converters

import androidx.room.TypeConverter
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.image.AnimeImage
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import kotlinx.serialization.json.Json

class AnimeConverters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromAnimeImage(image: AnimeImage): String {
        return json.encodeToString(image)
    }

    @TypeConverter
    fun toAnimeImage(imageString: String): AnimeImage {
        return json.decodeFromString(imageString)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return json.encodeToString(list)
    }

    @TypeConverter
    fun toStringList(listString: String): List<String> {
        return json.decodeFromString(listString)
    }

    @TypeConverter
    fun fromAnimeGenreList(list: List<AnimeGenre>): String {
        return json.encodeToString(list)
    }

    @TypeConverter
    fun toAnimeGenreList(listString: String): List<AnimeGenre> {
        return json.decodeFromString(listString)
    }

    @TypeConverter
    fun fromAnimeStudioList(list: List<AnimeStudio>): String {
        return json.encodeToString(list)
    }

    @TypeConverter
    fun toAnimeStudioList(listString: String): List<AnimeStudio> {
        return json.decodeFromString(listString)
    }
}