package club.anifox.android.feature.search.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.search.model.state.SearchUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal data class SearchUIPreviewParam(
    val onBackPressed: () -> Unit = { },
    val uiState: SearchUiState,
    val searchHistory: List<String>,
    val randomAnime: StateListWrapper<AnimeLight>,
    val onQueryChange: (String) -> Unit = { },
    val onTrailingIconClick: () -> Unit = { },
    val onAnimeClick: (String) -> Unit = { },
    val searchResults: Flow<PagingData<AnimeLight>>,
    val onHistoryItemClick: (String) -> Unit = { },
    val onDeleteHistoryClick: () -> Unit = { },
    val onRandomAnimeClick: (String) -> Unit = { },
    val onRefreshRandomAnimeClick: () -> Unit = { },
)

internal class SearchUIProvider :
    PreviewParameterProvider<SearchUIPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<SearchUIPreviewParam>
        get() = listOf(
            SearchUIPreviewParam(
                uiState = SearchUiState(),
                randomAnime = StateListWrapper(data = GlobalParams.DataSetAnimeLight),
                searchHistory = listOf("First", "Second", "Third"),
                searchResults = flow { emit(PagingData.from(listOf())) },
            ),
            SearchUIPreviewParam(
                uiState = SearchUiState(
                    query = GlobalParams.DataSetAnimeLight.first().title,
                ),
                randomAnime = StateListWrapper(),
                searchHistory = listOf(),
                searchResults = flow {
                    emit(
                        PagingData.from(
                            data = GlobalParams.DataSetAnimeLight,
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
                                append = LoadState.NotLoading(false),
                                prepend = LoadState.NotLoading(false),
                            )
                        )
                    )
                },
            )
        ).asSequence()
}