package club.anifox.android.feature.catalog.model

import club.anifox.android.feature.catalog.R

enum class FilterType(val displayTitleId: Int, val isShow: Boolean = true) {
    YEAR(R.string.feature_catalog_filter_year_title),
    GENRE(R.string.feature_catalog_filter_genre_title),
    TYPE(R.string.feature_catalog_filter_type_title),
    STATUS(R.string.feature_catalog_filter_status_title),
    TRANSLATION(R.string.feature_catalog_filter_translation_title),
}