package club.anifox.android.domain.model.anime.enum

enum class AnimeOrder(private val russianName: String) {
    Update("По дате обновления"),
    Aired("По дате добавления"),
    Released("По дате выхода"),
    Random("Рандом"),
    Rating("Рейтинг"),
    ;

    override fun toString(): String {
        return russianName
    }
}
