package su.anifox.domain.model.anime.enum

enum class AnimeType(private val russianName: String) {
    TV("Сериал"),
    MOVIE("Фильм"),
    OVA("OVA"),
    ONA("ONA"),
    SPECIAL("Специальный выпуск"),
    MUSIC("Музыка");

    override fun toString(): String = russianName
}