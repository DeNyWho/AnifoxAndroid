package club.anifox.android.feature.catalog.components.top

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.icon.AnifoxIcons
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.feature.catalog.R

@Composable
internal fun CatalogTopBarComponent(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Boolean,
    onSearchClick: () -> Unit,
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
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            AnifoxIconPrimary(
                modifier = Modifier
                    .clickableWithoutRipple {
                        onBackPressed.invoke()
                    }
                    .size(24.dp),
                imageVector = Filled.ArrowBack,
                contentDescription = "back",
            )

            Text(
                text = stringResource(R.string.feature_catalog_title),
                style = MaterialTheme.typography.titleLarge,
            )

            AnifoxIconPrimary(
                modifier = Modifier
                    .clickableWithoutRipple {
                        onSearchClick.invoke()
                    }
                    .size(24.dp),
                imageVector = AnifoxIcons.search,
                contentDescription = "search",
            )
        }
    }
}