package club.anifox.android.domain.model.user

import club.anifox.android.domain.model.common.device.FontSizePrefs
import kotlinx.serialization.Serializable

@Serializable
data class UISettings(
    val fontSizePrefs: FontSizePrefs = FontSizePrefs.DEFAULT,
)