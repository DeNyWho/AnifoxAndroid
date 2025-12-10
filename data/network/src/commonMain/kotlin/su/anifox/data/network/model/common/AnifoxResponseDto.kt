package su.anifox.data.network.model.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AnifoxResponseDto<T>(
    @SerialName("data")
    val data: List<T>,
    @SerialName("pageInfo")
    val pageInfo: AnifoxPaginationDto,
)