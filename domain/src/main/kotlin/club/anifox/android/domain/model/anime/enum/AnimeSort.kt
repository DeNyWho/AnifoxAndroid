package club.anifox.android.domain.model.anime.enum

enum class AnimeSort(private val russianName: String) {
    Desc("По убыванию"),
    Asc("По возрастанию");

    override fun toString(): String {
        return russianName
    }
}