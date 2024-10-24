package club.anifox.android.data.network.models.dto.anime.episodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeTranslationCountDTO(
    @SerialName("translation")
    val translation: AnimeTranslationDTO = AnimeTranslationDTO(),
    @SerialName("count_episodes")
    val countEpisodes: Int = 0,
)