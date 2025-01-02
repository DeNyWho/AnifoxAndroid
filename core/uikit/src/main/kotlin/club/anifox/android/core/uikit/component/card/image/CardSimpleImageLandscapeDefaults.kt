package club.anifox.android.core.uikit.component.card.image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

object CardSimpleImageLandscapeDefaults {
    object Width {
        val Default = 170.dp
        val GridPictures = 120.dp
        val GridScreenshot = 160.dp
    }

    object Height {
        val Default = 100.dp
        val GridPictures = 80.dp
        val GridScreenshot = 100.dp
    }

    object HorizontalArrangement {
        val Default = Arrangement.spacedBy(12.dp)
        val GridPictures = Arrangement.spacedBy(8.dp)
        val GridScreenshot = Arrangement.spacedBy(16.dp)
    }

    object VerticalArrangement {
        val Default = Arrangement.spacedBy(12.dp)
        val GridPictures = Arrangement.spacedBy(8.dp)
        val GridScreenshot = Arrangement.spacedBy(16.dp)
    }
}