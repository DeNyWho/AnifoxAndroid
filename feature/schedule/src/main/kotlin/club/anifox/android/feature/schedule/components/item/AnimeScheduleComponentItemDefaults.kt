package club.anifox.android.feature.schedule.components.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

internal object AnimeScheduleComponentItemDefaults {
    object Width {
        val Small = 110.dp
        val Medium = 120.dp
        val Large = 140.dp
    }

    object Height {
        val Small = 150.dp
        val Medium = 160.dp
        val Large = 190.dp
    }

    object HorizontalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }

    object VerticalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }
}