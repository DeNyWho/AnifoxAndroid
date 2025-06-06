package club.anifox.android.core.uikit.param

import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason.Fall
import club.anifox.android.domain.model.anime.enum.AnimeStatus.Ongoing
import club.anifox.android.domain.model.anime.enum.AnimeType.Tv
import club.anifox.android.domain.model.anime.enum.VideoType
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeEpisodeTranslation
import club.anifox.android.domain.model.anime.translations.AnimeTranslation
import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.model.character.role.CharacterRole
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import java.util.UUID

object GlobalParams {
    val Genres: ImmutableList<AnimeGenre> = listOf(
        AnimeGenre(id = UUID.randomUUID().toString(), name = "Приключения"),
        AnimeGenre(id = UUID.randomUUID().toString(), name = "Романтика")
    ).toImmutableList()
    val Studios: ImmutableList<AnimeStudio> = listOf(
        AnimeStudio(id = UUID.randomUUID().toString(), name = "Madhouse"),
        AnimeStudio(id = UUID.randomUUID().toString(), name = "TEST studio")
    ).toImmutableList()
    val TranslationsCount: ImmutableList<AnimeTranslationsCount> = listOf(
        AnimeTranslationsCount(
            translation = AnimeTranslation(
                id = 610,
                title = "AniLibria.TV",
                voice = "voice",
            ),
            countEpisodes = 24,
            link = null,
        ),
        AnimeTranslationsCount(
            translation = AnimeTranslation(
                id = 609,
                title = "AniDUB",
                voice = "voice",
            ),
            countEpisodes = 21,
            link = null,
        ),
    ).toImmutableList()

    val DataAnimeDetail = AnimeDetail(
        title = "Провожающая в последний путь Фрирен",
        url = "provozhaiushchaia-v-poslednii-put-friren",
        titleEnglish = listOf("Frieren: Beyond Journey's End").toImmutableList(),
        description = "Одержав победу над Королём демонов, отряд героя Химмеля вернулся домой. Приключение, растянувшееся на десятилетие, подошло к завершению. Волшебница-эльф Фрирен и её отважные товарищи принесли людям мир и разошлись в разные стороны, чтобы спокойно прожить остаток жизни. Однако не всех членов отряда ждёт одинаковая участь. Для эльфов время течёт иначе, поэтому Фрирен вынужденно становится свидетелем того, как её спутники один за другим постепенно уходят из жизни. Девушка осознала, что годы, проведённые в отряде героя, пронеслись в один миг, как падающая звезда в бескрайнем космосе её жизни, и столкнулась с сожалениями об упущенных возможностях. Сможет ли она смириться со смертью друзей и понять, что значит жизнь для окружающих её людей? Фрирен начинает новое путешествие, чтобы найти ответ.",
        genres = Genres,
        studios = Studios,
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

    val DataAnimeLightSingle = AnimeLight(
        title = "Провожающая в последний путь Фрирен",
        image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
        url = "provozhaiushchaia-v-poslednii-put-friren",
    )

    val DataAnimeLightFavouriteSingle = AnimeLightFavourite(
        title = "Провожающая в последний путь Фрирен",
        image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
        url = "provozhaiushchaia-v-poslednii-put-friren",
    )

    val DataAnimeVideosLightSingle = AnimeVideosLight(
        url = "https://...",
        imageUrl = "https://...",
        playerUrl = "https://...",
        name = "video",
        type = VideoType.Trailer,
    )

    val DataAnimeEpisodeSingle = AnimeEpisodesLight(
        title = "Конец путешествия",
        number = 1,
        image = "https://cdn.anifox.club/images/anime/episodes/provozhaiushchaia-v-poslednii-put-friren/c4ca4238a0b923820dcc509a6f75849b.jpg",
        aired = LocalDate.of(2023, 9, 29),
        description = "Мир празднует поражение Короля Демонов от рук Героя и его спутников. Теперь, когда их великое приключение закончилось, что будет делать маг Фрирен дальше?",
        filler = false,
        recap = false,
        translation = listOf(
            AnimeEpisodeTranslation(
                id = 609,
                link = "//kodik.info/serial/54025/05d5fd4731cc21947948187e34335d55/720p?episode=1",
                title = "AniDUB",
            )
        )
    )

    val DataSetAnimeLight = List(10) {
        AnimeLight(
            title = "Провожающая в последний путь Фрирен",
            image = "https://cdn.anifox.club/images/anime/medium/provozhaiushchaia-v-poslednii-put-friren/06182440ee23094f70d31e302cdc6116.jpg",
            url = "provozhaiushchaia-v-poslednii-put-friren$it",
        )
    }.toImmutableList()

    val DataSetRelationLight = List(10) {
        AnimeRelatedLight(
            anime = AnimeLight(
                title = "Провожающая в последний путь Фрирен",
                image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
                url = "provozhaiushchaia-v-poslednii-put-friren$it"
            ),
            type = "type",
        )
    }.toImmutableList()

    val DataSetCharactersLight = List(10) {
        AnimeCharactersLight(
            id = UUID.randomUUID().toString(),
            role = "Второстепенная",
            name = "Фрирен",
            image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
        )
    }.toImmutableList()

    val DataCharacterFull = CharacterFull(
        id = UUID.randomUUID().toString(),
        name = "",
        nameEn = "Frieren",
        nameKanji = "フリーレン",
        about = "Фрирен был магом в отряде Героя Химмеля. Они вместе со жрецом Хейтером и воином Эйзеном отправились в десятилетнее путешествие, чтобы победить Короля демонов. Она была последней, кого завербовали в отряд, и, несмотря на первоначальное впечатление Хайтера о том, что у нее средняя мана, Химмель подозревал, что она была самым могущественным магом, которого он когда-либо встречал.\\n\\n Будучи эльфийкой, прожившей более тысячи лет, Фрирен с трудом формировалась значимые отношения с людьми из-за разницы в продолжительности их жизни и в том, как они воспринимают течение времени. Обычно это проявляется как лень, поскольку пропуск важного события не имеет значения, поскольку у нее будет масса возможностей пережить его заново в будущем, или как почти социопатическая неспособность понять чувства человека. Например, она склонна не замечать, как отсутствие у нее нетерпения или холодные высказывания могут повлиять на окружающих ее людей. Члены ее партии также часто заявляли, что не могут понять ее чувств или прочитать, что происходит у нее в голове.\\n\\nНесмотря на то, что Фрирен является чрезвычайно могущественным магом, он имеет привычку собирать редкую магию, ориентированную на ежедневное использование, например, магию, которая может отполировать бронзовую статую или заставить распуститься цветы. Она всегда просит гримуар в качестве награды, даже если уже знает его содержимое, или, когда речь заходит о потерянных гримуарах Великого мага Фламме, знает, что они подделки.После смерти Героя Химмеля и священника Хайтера Фрирен решила, что хочет узнать больше о людях. В настоящее время она путешествует со своей ученицей Ферн, беженкой с войны, оставленной на ее попечение священником Хейтером, в поисках Энде, места, где обитают души на самой северной оконечности континента, и где был построен замок Короля демонов, чтобы попытаться поговорить с душой Химмеля. В своем новом путешествии Фрирен посещает многие места, где в прошлом побывала ее бывшая группа, и вновь переживает воспоминания, постепенно все больше понимая их значение и свои чувства.\\n\\nПо иронии судьбы, Ферн, как правило, заботится о Фрирен в повседневной жизни, несмотря на то, что она намного моложе.\\n\\n\\n(Источник: Frieren: Beyond Journey's End Wiki)",
        roles = listOf(
            CharacterRole(
                role = "Главная",
                anime = DataAnimeLightSingle,
            ),
        ),
        image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
        pictures = listOf("")
    )
}