package su.anifox.domain.model.anime.enum

enum class VideoType(private val russianName: String) {
    TRAILER("Трейлер"),
    PV("PV"),
    CM("CM");

    override fun toString(): String = russianName
}