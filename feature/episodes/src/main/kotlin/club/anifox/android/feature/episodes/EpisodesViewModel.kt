package club.anifox.android.feature.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight
import club.anifox.android.domain.usecase.anime.paging.anime.episodes.AnimeEpisodesPagingUseCase
import club.anifox.android.feature.episodes.data.EpisodesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EpisodesViewModel @Inject constructor(
    private val animeEpisodesPagingUseCase: AnimeEpisodesPagingUseCase,
): ViewModel() {
    private val _episodesState = MutableStateFlow(EpisodesState())
    val episodesState = _episodesState.asStateFlow()

    val loadState = MutableStateFlow<CombinedLoadStates?>(null)

    fun updateLoadingState(isLoading: Boolean) {
        _episodesState.update { it.copy(isLoading = isLoading) }
    }

    @OptIn(FlowPreview::class)
    val episodesResults: Flow<PagingData<AnimeEpisodesLight>> = _episodesState
        .onStart { _episodesState.update { it.copy(isLoading = true) } }
        .debounce(0)
        .filter { it.isInitialized }
        .distinctUntilChanged()
        .flatMapLatest { state ->
            animeEpisodesPagingUseCase(
                url = state.url,
                translationId = state.translationId,
            )
        }

    fun updateLoadState(loadState: CombinedLoadStates) {
        this.loadState.value = loadState
    }

    fun initializeFilter(url: String, translationId: Int) {
        viewModelScope.launch {
            _episodesState.update { state ->
                state.copy(
                    url = url,
                    translationId = translationId,
                    isInitialized = true,
                    isLoading = state.isLoading,
                )
            }
        }
    }

}