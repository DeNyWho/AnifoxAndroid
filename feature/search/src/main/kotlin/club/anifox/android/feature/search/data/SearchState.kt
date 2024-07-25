package club.anifox.android.feature.search.data

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val query: String = "",
    val status: AnimeStatus? = null,
    val type: AnimeType? = null,
    val year: Int? = null,
    val season: AnimeSeason? = null,
    val searchResults: Flow<PagingData<AnimeLight>>
)