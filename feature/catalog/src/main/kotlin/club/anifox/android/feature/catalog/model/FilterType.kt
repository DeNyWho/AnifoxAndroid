package club.anifox.android.feature.catalog.model

import club.anifox.android.feature.catalog.R

internal enum class FilterType(val displayTitleId: Int, val isShow: Boolean = true) {
    GENRE(R.string.feature_catalog_filter_genre_title),
    STUDIO(R.string.feature_catalog_filter_studio_title),
    YEARS(R.string.feature_catalog_filter_year_title),
    TYPE(R.string.feature_catalog_filter_type_title),
    STATUS(R.string.feature_catalog_filter_status_title),
    TRANSLATION(R.string.feature_catalog_filter_translation_title),
    SEASON(R.string.feature_catalog_filter_season_title),
    ORDER(R.string.feature_catalog_filter_order_title),
    SORT(R.string.feature_catalog_filter_sort_title),
//    FILTER(R.string.feature_catalog_filter_filter_title),
}