package club.anifox.android.domain.model.anime.enum

enum class AnimeStatus(private val russianName: String) {
    Released("Вышел"),
    Ongoing("Онгоинг");

    override fun toString(): String {
        return russianName
    }
}