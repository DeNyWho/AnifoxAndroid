package club.anifox.android.domain.model.anime.related

import club.anifox.android.domain.model.anime.AnimeLight

data class AnimeRelatedLight(
    val anime: AnimeLight,
    val type: String,
)