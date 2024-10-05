package club.anifox.android.domain.model.anime.enum

enum class SortEnum(private val russianName: String) {
    Desc("По убыванию"),
    Asc("По возрастанию");

    override fun toString(): String {
        return russianName
    }
}