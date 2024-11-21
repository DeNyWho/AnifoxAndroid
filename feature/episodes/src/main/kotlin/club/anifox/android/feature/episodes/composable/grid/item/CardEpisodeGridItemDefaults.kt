package club.anifox.android.feature.episodes.composable.grid.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

internal object CardEpisodeGridItemDefaults {
    object Width {
        val Min = 160.dp
        val GridSmall = 180.dp
        val GridMedium = 200.dp
        val GridLarge = 220.dp
    }

    object HorizontalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }

    object VerticalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }
}