package club.anifox.android.domain.model.anime.enum

enum class OrderEnum (private val russianName: String) {
    Update("По обновлению"),
    Aired("По дате"),
    Released("По дате"),
    Random("Рандом")
    ;

    override fun toString(): String {
        return russianName
    }
}
