package su.anifox.domain.model.common.api

data class AnifoxPagination(
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val limit: Int,
    val page: Int,
    val totalCount: Int,
    val totalPages: Int,
)