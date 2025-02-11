package club.anifox.android.feature.settings.component.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import club.anifox.android.domain.model.common.device.PlayerOrientation
import club.anifox.android.feature.settings.R

@Composable
internal fun PlayerComponent(
    modifier: Modifier = Modifier,
    selectedPlayerOrientationState: PlayerOrientation,
    updatePlayerOrientation: () -> Unit,
) {
    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = stringResource(R.string.feature_settings_section_player_title),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge,
    )

    Column(
        modifier = modifier
            .clickable {
                updatePlayerOrientation.invoke()
            },
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    text = stringResource(R.string.feature_settings_section_player_horizontal_orientation_title),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = stringResource(R.string.feature_settings_section_player_horizontal_orientation_description),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray.copy(0.1f)),
                )
            }

            Switch(
                modifier = Modifier
                    .width(40.dp),
                checked = selectedPlayerOrientationState == PlayerOrientation.HORIZONTAL,
                onCheckedChange = {
                    updatePlayerOrientation.invoke()
                },
                colors = SwitchDefaults.colors(),
            )
        }
    }
}