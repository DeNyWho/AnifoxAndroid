package club.anifox.android.feature.catalog.composable.top

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnSurface

@Composable
internal fun CatalogTopBar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Boolean,
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            AnifoxIconOnSurface(
                modifier = Modifier
                    .clickable {
                        onBackPressed.invoke()
                    }
                    .size(24.dp),
                imageVector = Filled.ArrowBack,
                contentDescription = "back",
            )

            Text(
                text = "All",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(Modifier.size(24.dp))
        }
    }
}