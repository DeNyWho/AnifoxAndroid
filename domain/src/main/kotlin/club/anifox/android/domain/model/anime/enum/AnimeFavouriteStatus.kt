package club.anifox.android.domain.model.anime.enum

enum class AnimeFavouriteStatus(private val russianName: String) {
    WATCHING("Смотрю"),
    IN_PLAN("В планах"),
    WATCHED("Просмотрено"),
    POSTPONED("Брошено"),
    ;

    override fun toString(): String {
        return russianName
    }
}