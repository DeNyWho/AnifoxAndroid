package club.anifox.android.data.network.models.dto.anime.light

import club.anifox.android.data.network.models.dto.anime.common.AnimeImageDTO
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeLightDTO(
    @SerialName("title")
    val title: String = "",
    @SerialName("image")
    val image: AnimeImageDTO = AnimeImageDTO(),
    @SerialName("url")
    val url: String = "",
    @SerialName("type")
    val type: AnimeType = AnimeType.Tv,
    @SerialName("rating")
    val rating: Double? = null,
    @SerialName("year")
    val year: Int = 0,
    @SerialName("status")
    val status: AnimeStatus = AnimeStatus.Ongoing,
    @SerialName("season")
    val season: AnimeSeason = AnimeSeason.Fall,
)
