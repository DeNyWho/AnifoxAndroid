package club.anifox.android.commonui.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

object CardAnimePortraitDefaults {
    object Width {
        val Default = 140.dp
        val Small = 120.dp
    }

    object Height {
        val Default = 190.dp
        val Grid = 180.dp
        val Small = 170.dp
    }

    object HorizontalArrangement {
        val Default = Arrangement.spacedBy(12.dp)
        val Grid = Arrangement.spacedBy(6.dp)
    }
}