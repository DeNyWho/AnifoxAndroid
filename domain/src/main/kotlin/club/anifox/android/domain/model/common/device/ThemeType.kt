package club.anifox.android.domain.model.common.device

enum class ThemeType(private val russianName: String) {
    SYSTEM("Системная"),
    LIGHT("Светлая"),
    DARK("Тёмная"),
    ;

    override fun toString(): String {
        return russianName
    }

    companion object {
        fun fromString(value: String): ThemeType? {
            return entries.find { it.toString() == value }
        }
    }
}