package club.anifox.android.feature.episodes.components.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

internal object CardEpisodeGridComponentItemDefaults {
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