package club.anifox.android.feature.detail.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason.Fall
import club.anifox.android.domain.model.anime.enum.AnimeStatus.Ongoing
import club.anifox.android.domain.model.anime.enum.AnimeType.Tv
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper

internal data class DetailContentPreviewParam(
    val modifier: Modifier = Modifier,
    val detailAnime: StateWrapper<AnimeDetail>,
    val screenshotsAnime: StateListWrapper<String>,
    val relationAnime: StateListWrapper<AnimeRelatedLight>,
    val similarAnime: StateListWrapper<AnimeLight>,
    val onBackPressed: () -> Boolean = { true },
    val onAnimeClick: (String) -> Unit,
    val onScreenshotClick: (String) -> Unit,
)

internal val Data = AnimeDetail(
    title = "Провожающая в последний путь Фрирен",
    url = "provozhaiushchaia-v-poslednii-put-friren",
    titleEnglish = listOf("Frieren: Beyond Journey's End"),
    description = "Одержав победу над Королём демонов, отряд героя Химмеля вернулся домой. Приключение, растянувшееся на десятилетие, подошло к завершению. Волшебница-эльф Фрирен и её отважные товарищи принесли людям мир и разошлись в разные стороны, чтобы спокойно прожить остаток жизни. Однако не всех членов отряда ждёт одинаковая участь. Для эльфов время течёт иначе, поэтому Фрирен вынужденно становится свидетелем того, как её спутники один за другим постепенно уходят из жизни. Девушка осознала, что годы, проведённые в отряде героя, пронеслись в один миг, как падающая звезда в бескрайнем космосе её жизни, и столкнулась с сожалениями об упущенных возможностях. Сможет ли она смириться со смертью друзей и понять, что значит жизнь для окружающих её людей? Фрирен начинает новое путешествие, чтобы найти ответ.",
    genre = listOf(AnimeGenre(name = "Приключения"), AnimeGenre(name = "Романтика")),
    studio = listOf(AnimeStudio(name = "Студия"), AnimeStudio(name = "Вторая студия")),
    type = Tv,
    rating = 4.8,
    ratingMpa = "R",
    minimalAge = 18,
    year = 2024,
    status = Ongoing,
    season = Fall,
    episodes = 12,
    episodesAired = 8,
)

private val DataSetAnimeLight = List(10) {
    AnimeLight(
        title = "Провожающая в последний путь Фрирен",
        image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
        url = "provozhaiushchaia-v-poslednii-put-friren$it",
    )
}

private val DataSetRelationLight = List(10) {
    AnimeRelatedLight(
        anime = AnimeLight(
            title = "Провожающая в последний путь Фрирен",
            image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
            url = "provozhaiushchaia-v-poslednii-put-friren$it"
        ),
        type = "type",
    )
}

internal class DetailContentProvider:
    PreviewParameterProvider<DetailContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<DetailContentPreviewParam>
        get() = listOf(
//            DetailContentPreviewParam(
//                modifier = Modifier,
//                detailAnime = StateWrapper.loading(),
//            ),
            DetailContentPreviewParam(
                modifier = Modifier,
                detailAnime = StateWrapper(data = Data, isLoading = false),
                relationAnime = StateListWrapper(data = DataSetRelationLight, isLoading = false),
                screenshotsAnime = StateListWrapper(isLoading = false),
                similarAnime = StateListWrapper(data = DataSetAnimeLight, isLoading = false),
                onAnimeClick = { },
                onScreenshotClick = { },
            )
        ).asSequence()
}