@file:UseSerializers(LocalDateSerializer::class)
package club.anifox.android.data.network.models.dto.anime.episodes

import club.anifox.android.core.common.serializer.LocalDateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
data class AnimeEpisodesDTO(
    @SerialName("title")
    val title: String = "",
    @SerialName("number")
    val number: Int = 0,
    @SerialName("image")
    val image: String = "",
    @SerialName("aired")
    val aired: LocalDate = LocalDate.now(),
    @SerialName("description")
    val description: String = "",
    @SerialName("filler")
    val filler: Boolean = false,
    @SerialName("recap")
    val recap: Boolean = false,
    @SerialName("translations")
    val translations: List<AnimeEpisodesTranslationsDTO> = listOf(),
)
