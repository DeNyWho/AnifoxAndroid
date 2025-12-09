package su.anifox.domain.model.anime.enum

enum class RatingMpa(
    private val russianName: String,
    private val description: String,
    val graphqlValue: String
) {
    G(
        russianName = "G - Без ограничений",
        description = "Подходит для всех возрастов",
        graphqlValue = "G"
    ),
    PG(
        russianName = "PG - Рекомендуется присутствие родителей",
        description = "Некоторый материал может не подходить для детей",
        graphqlValue = "PG"
    ),
    PG_13(
        russianName = "PG-13 - Детям до 13 лет просмотр не желателен",
        description = "Некоторый материал может быть неуместным для детей до 13 лет",
        graphqlValue = "PG_13"
    ),
    R(
        russianName = "R - Ограничено",
        description = "Лицам до 17 лет обязательно присутствие взрослого",
        graphqlValue = "R"
    ),
    R_PLUS(
        russianName = "R+ - Только для взрослых",
        description = "Содержит материал для взрослой аудитории 17+",
        graphqlValue = "R_PLUS"
    );

    override fun toString(): String = russianName

    fun getDescription(): String = description

    fun getMinimalAge(): Int {
        return when (this) {
            G -> 0
            PG -> 7
            PG_13 -> 13
            R -> 17
            R_PLUS -> 18
        }
    }

    companion object {
        fun fromGraphQL(value: String): RatingMpa? {
            return entries.find { it.graphqlValue == value }
        }

        fun fromMinimalAge(age: Int): RatingMpa {
            return when {
                age < 7 -> G
                age < 13 -> PG
                age < 17 -> PG_13
                age < 18 -> R
                else -> R_PLUS
            }
        }

        fun getAllRatings(): List<RatingMpa> = entries
    }
}