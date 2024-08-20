package club.anifox.android.domain.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("user_data")
data class UserData(
    @SerialName("is_first_launch")
    val isFirstLaunch: Boolean = true,
)
