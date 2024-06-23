package club.anifox.android.domain.model.anime.enum

enum class AnimeType(private val russianName: String) {
    Movie("Фильм"),
    Ona("Ona"),
    Ova("Ova"),
    Music("Музыка"),
    Special("Специальный выпуск"),
    Tv("Сериал");

    override fun toString(): String {
        return russianName
    }
}