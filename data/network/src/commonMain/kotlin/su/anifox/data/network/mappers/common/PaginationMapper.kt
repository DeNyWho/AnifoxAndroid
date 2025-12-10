package su.anifox.data.network.mappers.common

import su.anifox.data.network.model.common.AnifoxResponseDto
import su.anifox.domain.model.common.api.AnifoxPagination

internal object PaginationMapper {
    fun <T, R> AnifoxResponseDto<T>.toDomain(
        mapper: (T) -> R
    ): Pair<List<R>, AnifoxPagination> {
        return Pair(
            data.map(mapper),
            AnifoxPagination(
                hasNextPage = pageInfo.hasNextPage,
                hasPreviousPage = pageInfo.hasPreviousPage,
                limit = pageInfo.limit,
                page = pageInfo.page,
                totalCount = pageInfo.totalCount,
                totalPages = pageInfo.totalPages,
            )
        )
    }
}