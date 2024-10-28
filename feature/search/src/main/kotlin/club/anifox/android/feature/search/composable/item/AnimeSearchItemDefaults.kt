package club.anifox.android.feature.search.composable.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

internal object AnimeSearchItemDefaults {
    object Width {
        val Small = 120.dp
        val Medium = 140.dp
        val Large = 180.dp
    }

    object Height {
        val Small = 170.dp
        val Medium = 200.dp
        val Large = 240.dp
    }

    object HorizontalArrangement {
        val Default = Arrangement.spacedBy(16.dp)
    }

    object VerticalArrangement {
        val Default = Arrangement.spacedBy(16.dp)
    }
}