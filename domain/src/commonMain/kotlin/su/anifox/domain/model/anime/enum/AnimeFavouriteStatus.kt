package su.anifox.domain.model.anime.enum

enum class AnimeFavouriteStatus(private val russianName: String) {
    WATCHING("Смотрю"),
    COMPLETED("Просмотрено"),
    ON_HOLD("Отложено"),
    DROPPED("Брошено"),
    PLAN_TO_WATCH("В планах");

    override fun toString(): String = russianName

    companion object {
        const val NOT_WATCHING = "Не смотрю"

        fun getAllStatuses(): List<String> {
            return listOf(NOT_WATCHING) + entries.map { it.toString() }
        }

        fun fromString(value: String): AnimeFavouriteStatus? {
            return if (value == NOT_WATCHING) null
            else entries.find { it.toString() == value }
        }
    }
}