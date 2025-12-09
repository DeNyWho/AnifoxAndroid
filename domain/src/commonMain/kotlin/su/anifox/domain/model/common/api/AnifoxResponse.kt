package su.anifox.domain.model.common.api

import androidx.compose.runtime.Immutable

@Immutable
data class AnifoxResponse<T>(
    val data: List<T>,
    val pageInfo: AnifoxPagination,
)