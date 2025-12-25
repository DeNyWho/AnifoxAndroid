package su.anfiox.core.uikit.util

import androidx.compose.runtime.staticCompositionLocalOf
import su.anifox.domain.model.common.device.ScreenInfo

val LocalScreenInfo = staticCompositionLocalOf<ScreenInfo> {
    error("LocalScreenInfo not provided")
}