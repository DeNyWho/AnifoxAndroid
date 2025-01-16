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

    companion object {
        const val NOT_WATCHING = "Не смотрю"

        fun getAllStatuses(): List<String> {
            return listOf(NOT_WATCHING) + entries.map { it.toString() }
        }

        fun fromString(value: String): AnimeFavouriteStatus? {
            return if (value == NOT_WATCHING) {
                null
            } else {
                entries.find { it.toString() == value }
            }
        }
    }
}