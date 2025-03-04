package club.anifox.android.feature.search.model.state

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import kotlinx.coroutines.flow.Flow

internal data class SearchUiState(
    val query: String = "",
    val error: String? = null,
    val lastSearchedQuery: String = "",
    val pagingData: Flow<PagingData<AnimeLight>>? = null,
    val isSearchBarFocused: Boolean = false,
)