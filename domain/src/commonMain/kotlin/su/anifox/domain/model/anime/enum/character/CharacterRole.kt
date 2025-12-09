package su.anifox.domain.model.anime.enum.character

enum class CharacterRole(private val russianName: String) {
    MAIN("Главный"),
    SUPPORTING("Второстепенный");

    override fun toString(): String = russianName
}