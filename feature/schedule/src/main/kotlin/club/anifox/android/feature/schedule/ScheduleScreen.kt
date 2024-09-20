package club.anifox.android.feature.schedule

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.util.DefaultPreview

@Composable
internal fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel(),
) {

}

@Composable
private fun ScheduleUI() {

}

@PreviewScreenSizes
@Composable
private fun PreviewScheduleScreenUI() {
    DefaultPreview(true) {
        ScheduleUI()
    }
}
