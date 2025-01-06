package club.anifox.android.core.uikit.component.card.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

object CardCharactersItemDefaults {
    object Width {
        val Default = 72.dp
    }

    object Height {
        val Default = 72.dp
    }

    object GridItemSpan {
        val Default = 8.dp
    }

    object HorizontalArrangement {
        val Default = Arrangement.spacedBy(8.dp)
        val Grid = Arrangement.spacedBy(8.dp)
    }

    object VerticalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }
}