package club.anifox.android.feature.schedule.composable.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.feature.schedule.R

@Composable
internal fun ScheduleEmptyContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.feature_schedule_empty_title),
            maxLines = 1,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = stringResource(R.string.feature_schedule_empty_subtitle),
            maxLines = 1,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
private fun PreviewFavouriteEmptyContent() {
    DefaultPreview {
        ScheduleEmptyContent()
    }
}