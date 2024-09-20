package club.anifox.android.core.uikit.component.card.anime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

object CardAnimePortraitDefaults {
    object Width {
        val Default = 140.dp
        val Grid = 100.dp
        val Small = 120.dp
    }

    object Height {
        val Default = 190.dp
        val Grid = 160.dp
        val Small = 170.dp
    }

    object HorizontalArrangement {
        val Default = Arrangement.spacedBy(12.dp)
        val Grid = Arrangement.spacedBy(16.dp)
    }

    object VerticalArrangement {
        val Grid = Arrangement.spacedBy(16.dp)
    }
}