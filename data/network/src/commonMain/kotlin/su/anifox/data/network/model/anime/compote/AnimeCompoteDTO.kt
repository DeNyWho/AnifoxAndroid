package su.anifox.data.network.model.anime.compote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import su.anifox.data.network.model.anime.common.AnimeGenreDto
import su.anifox.data.network.model.anime.common.AnimeStudioDto

@Serializable
internal data class AnimeCompoteDTO(
    @SerialName("genres")
    val genres: List<AnimeGenreDto>,
    @SerialName("studios")
    val studios: List<AnimeStudioDto>,
)