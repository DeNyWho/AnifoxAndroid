package club.anifox.android.core.uikit.component.card.anime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

object CardAnimePortraitDefaults {
    object Width {
        val Min = 80.dp
        val Default = 140.dp
        val GridSmall = 120.dp
        val GridMedium = 140.dp
        val GridLarge = 180.dp
        val Small = 120.dp
    }

    object Height {
        val Default = 190.dp
        val GridSmall = 160.dp
        val GridMedium = 180.dp
        val GridLarge = 240.dp
        val Small = 170.dp
    }

    object GridItemSpan {
        val Default = 8.dp
    }

    object HorizontalArrangement {
        val Default = Arrangement.spacedBy(12.dp)
        val Grid = Arrangement.spacedBy(8.dp)
    }

    object VerticalArrangement {
        val Grid = Arrangement.spacedBy(4.dp)
        val GridSearch = Arrangement.spacedBy(12.dp)
    }

    object Limit {
        const val LANDSCAPE_LIMIT = 11
    }
}