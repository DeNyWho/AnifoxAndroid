package club.anifox.android.domain.model.user

import club.anifox.android.domain.model.common.device.FontSizePrefs
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("user_data")
data class UserData(
    @SerialName("is_first_launch")
    val isFirstLaunch: Boolean = true,

    @SerialName("font_size_prefs")
    val fontSizePrefs: FontSizePrefs = FontSizePrefs.DEFAULT
)
