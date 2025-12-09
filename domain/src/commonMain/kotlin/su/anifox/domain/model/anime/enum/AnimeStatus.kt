package su.anifox.domain.model.anime.enum

enum class AnimeStatus(private val russianName: String) {
    ANNOUNCED("Анонсировано"),
    ONGOING("Онгоинг"),
    COMPLETED("Завершено");

    override fun toString(): String = russianName
}