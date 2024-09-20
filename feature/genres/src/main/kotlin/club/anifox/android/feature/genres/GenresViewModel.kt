package club.anifox.android.feature.genres

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeGenresUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class GenresViewModel @Inject constructor(
    private val animeGenresUseCase: GetAnimeGenresUseCase,
    private val animeUseCase: GetAnimeUseCase,
): ViewModel() {
    private val _genresAnime: MutableState<StateListWrapper<AnimeGenre>> =
        mutableStateOf(StateListWrapper())
    val genresAnime: MutableState<StateListWrapper<AnimeGenre>> = _genresAnime

    private val _animeByGenre: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper())
    val animeByGenre: MutableState<StateListWrapper<AnimeLight>> = _animeByGenre


}