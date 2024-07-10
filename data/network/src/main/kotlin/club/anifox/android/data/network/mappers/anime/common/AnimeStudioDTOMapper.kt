package club.anifox.android.data.network.mappers.anime.common

import club.anifox.android.data.network.models.dto.anime.common.AnimeStudioDTO
import club.anifox.android.domain.model.anime.studio.AnimeStudio

fun AnimeStudioDTO.toStudio(): AnimeStudio = AnimeStudio(id, name)