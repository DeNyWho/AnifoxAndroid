package club.anifox.android.domain.model.anime.enum

enum class AnimeSeason(private val russianName: String) {
    Winter("Зима"),
    Spring("Весна"),
    Summer("Лето"),
    Fall("Осень");

    override fun toString(): String {
        return russianName
    }
}
