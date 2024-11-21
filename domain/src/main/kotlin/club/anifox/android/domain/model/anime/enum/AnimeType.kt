package club.anifox.android.domain.model.anime.enum

enum class AnimeType(private val russianName: String) {
    Movie("Фильм"),
    Ona("Ona"),
    Ova("Ova"),
    Music("Музыка"),
    Tv("Сериал"),
    Special("Специальный выпуск");

    override fun toString(): String {
        return russianName
    }
}