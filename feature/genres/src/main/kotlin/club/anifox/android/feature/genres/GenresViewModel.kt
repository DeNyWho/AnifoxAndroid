package club.anifox.android.feature.genres

import androidx.lifecycle.ViewModel
import club.anifox.android.domain.usecase.anime.GetAnimeGenresUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class GenresViewModel @Inject constructor(
    private val animeGenresUseCase: GetAnimeGenresUseCase,
    private val animeUseCase: GetAnimeUseCase,
): ViewModel() {

}