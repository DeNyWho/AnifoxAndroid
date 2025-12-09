package su.anifox.domain.model.anime.enum

enum class AnimeSort(private val russianName: String) {
    DESC("По убыванию"),
    ASC("По возрастанию");

    override fun toString(): String {
        return russianName
    }
}