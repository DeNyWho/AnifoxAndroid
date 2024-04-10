package club.anifox.buildlogic.convention

enum class AnifoxBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}