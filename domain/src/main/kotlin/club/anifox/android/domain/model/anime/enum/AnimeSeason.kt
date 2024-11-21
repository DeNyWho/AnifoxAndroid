package club.anifox.android.domain.model.anime.enum

enum class AnimeSeason(private val russianName: String) {
    Winter("Зима"),
    Spring("Весна"),
    Summer("Лето"),
    Fall("Осень");

    override fun toString(): String {
        return russianName
    }

    companion object {
        fun fromMonth(month: Int): AnimeSeason {
            return when (month) {
                12, 1, 2 -> Winter
                3, 4, 5 -> Spring
                6, 7, 8 -> Summer
                9, 10, 11 -> Fall
                else -> throw IllegalArgumentException("Неверный номер месяца: $month")
            }
        }
    }
}
