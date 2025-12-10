package su.anifox.domain.model.anime.enum

enum class AnimeOrder(private val russianName: String) {
    UPDATED_AT("По дате обновления"),
    AIRED_ON("По дате добавления"),
    RELEASED_ON("По дате выхода"),
    RANDOM("Рандом"),
    RATING("Рейтинг"),
    ;

    override fun toString(): String {
        return russianName
    }
}
