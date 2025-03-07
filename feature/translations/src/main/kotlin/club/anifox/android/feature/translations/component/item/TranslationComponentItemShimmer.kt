package club.anifox.android.feature.translations.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.feature.translations.component.item.param.TranslationComponentItemPreviewParam
import club.anifox.android.feature.translations.component.item.param.TranslationComponentItemProvider
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds.Custom
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
internal fun TranslationComponentItemShimmer(
    modifier: Modifier = Modifier,
    shimmer: Shimmer = rememberShimmer(Custom),
) {
    val screenInfo = LocalScreenInfo.current
    val textHeight = 16.dp + screenInfo.fontSizePrefs.fontSizeExtra.dp

    Column(
        modifier = modifier
            .shimmer(shimmer)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(textHeight)
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )

            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(textHeight)
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )
        }

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .background(Color.LightGray.copy(0.1f))
                .fillMaxWidth(),
        )
    }
}

internal fun LazyListScope.showTranslationComponentItemShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
    count: Int = 4,
) {
    items(count) {
        TranslationComponentItemShimmer(
            modifier = modifier,
            shimmer = shimmerInstance,
        )
    }
}

@Preview
@Composable
private fun PreviewTranslationComponentItem(
    @PreviewParameter(TranslationComponentItemProvider::class)
    param: TranslationComponentItemPreviewParam,
) {
    DefaultPreview {
        TranslationComponentItemShimmer(
            shimmer = rememberShimmer(Custom),
        )
    }
}