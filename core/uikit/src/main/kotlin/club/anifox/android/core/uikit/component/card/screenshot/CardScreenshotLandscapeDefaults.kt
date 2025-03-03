package club.anifox.android.core.uikit.component.card.screenshot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

object CardScreenshotLandscapeDefaults {
    object Width {
        val Default = 170.dp
        val Grid = 160.dp
    }

    object Height {
        val Default = 100.dp
    }

    object HorizontalArrangement {
        val Default = Arrangement.spacedBy(12.dp)
        val Grid = Arrangement.spacedBy(12.dp)
    }

    object VerticalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }

    object Limit {
        const val LANDSCAPE_LIMIT = 5
    }
}