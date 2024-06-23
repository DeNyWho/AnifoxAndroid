package club.anifox.android.domain.model.anime.enum

enum class VideoType(private val russianName: String) {
    Trailer("Трейлер"),
    Opening("Опенинг"),
    Ending("Эндинг"),
    Other("Другое");

    override fun toString(): String {
        return russianName
    }
}