package su.anifox.data.network.model.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnifoxPaginationDto(
    @SerialName("hasNextPage")
    val hasNextPage: Boolean,
    @SerialName("hasPreviousPage")
    val hasPreviousPage: Boolean,
    @SerialName("limit")
    val limit: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("totalPages")
    val totalPages: Int,
)