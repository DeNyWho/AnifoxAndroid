package club.anifox.android.core.uikit.util

fun String.limitTo(maxLength: Int): String {
    return if (this.length > maxLength) {
        this.take(maxLength) + "..."
    } else {
        this
    }
}