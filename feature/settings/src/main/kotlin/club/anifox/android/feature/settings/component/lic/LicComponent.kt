package club.anifox.android.feature.settings.component.lic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import club.anifox.android.feature.settings.R

@Composable
internal fun LicComponent(
    openLicHolders: () -> Unit,
) {
    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = stringResource(R.string.feature_settings_section_lic_title),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge,
    )

    Column {
        Column(
            modifier = Modifier
                .clickable {
                    openLicHolders.invoke()
                }
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(R.string.feature_settings_section_lic_holders_title),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}