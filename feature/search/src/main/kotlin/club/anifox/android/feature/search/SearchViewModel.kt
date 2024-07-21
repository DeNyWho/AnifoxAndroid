package club.anifox.android.feature.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAnimeUseCase: GetAnimeUseCase,
) : ViewModel() {
    private val _searchedAnime: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper())
    val searchedAnime: MutableState<StateListWrapper<AnimeLight>> = _searchedAnime

}