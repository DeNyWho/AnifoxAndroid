package su.anifox.data.network.model.anime.episode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeEpisodeDto(
    @SerialName("id")
    val id: String,
    @SerialName("animeId")
    val animeId: String,
    @SerialName("number")
    val number: Int,
    @SerialName("title")
    val title: String? = null,
    @SerialName("titleEn")
    val titleEn: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("descriptionEn")
    val descriptionEn: String? = null,
    @SerialName("image")
    val image: String,
    @SerialName("aired")
    val aired: String? = null,
    @SerialName("duration")
    val duration: Int? = null,
    @SerialName("filler")
    val filler: Boolean = false,
    @SerialName("recap")
    val recap: Boolean = false,
    @SerialName("translations")
    val translations: List<EpisodeTranslationDto> = emptyList(),
)