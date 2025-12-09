package su.anifox.domain.model.anime.enum

enum class AnimeSeason(private val russianName: String) {
    WINTER("Зима"),
    SPRING("Весна"),
    SUMMER("Лето"),
    FALL("Осень");

    override fun toString(): String = russianName

    companion object {
        fun fromMonth(month: Int): AnimeSeason {
            return when (month) {
                12, 1, 2 -> WINTER
                3, 4, 5 -> SPRING
                6, 7, 8 -> SUMMER
                9, 10, 11 -> FALL
                else -> throw IllegalArgumentException("Неверный номер месяца: $month")
            }
        }
    }
}