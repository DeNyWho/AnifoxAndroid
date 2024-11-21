package club.anifox.android.core.uikit.util

import androidx.compose.runtime.staticCompositionLocalOf
import club.anifox.android.domain.model.common.device.ScreenInfo

val LocalScreenInfo = staticCompositionLocalOf<ScreenInfo> {
    error("LocalScreenInfo not provided")
}