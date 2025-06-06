package club.anifox.android.feature.search.component.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

internal object AnimeSearchComponentItemDefaults {
    object Width {
        val Small = 90.dp
        val Medium = 100.dp
        val Large = 120.dp
    }

    object Height {
        val Small = 120.dp
        val Medium = 130.dp
        val Large = 160.dp
    }

    object HorizontalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }

    object VerticalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }
}