package su.anifox.domain.model.anime.enum

enum class TranslationType(private val russianName: String) {
    VOICE("Озвучка"),
    SUB("Субтитры");

    override fun toString(): String = russianName
}