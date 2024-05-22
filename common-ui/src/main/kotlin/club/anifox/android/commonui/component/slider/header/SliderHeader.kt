package club.anifox.android.commonui.component.slider.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun SliderHeader(
    modifier: Modifier = Modifier,
    title: String = "Title",
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .heightIn(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
    }

}

@PreviewLightDark
@Composable
private fun SliderHeaderPreview() {
    SliderHeader()
}