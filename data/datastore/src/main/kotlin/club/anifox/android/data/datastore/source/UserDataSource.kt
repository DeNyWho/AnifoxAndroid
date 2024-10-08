package club.anifox.android.data.datastore.source

import androidx.datastore.core.DataStore
import club.anifox.android.domain.model.common.device.FontSizePrefs
import club.anifox.android.domain.model.user.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val dataStore: DataStore<UserData>,
) {
    val userData = buildMap<String, Flow<*>> {
        put("is_first_launch", dataStore.data.map { it.isFirstLaunch })
        put("font_size_prefs", dataStore.data.map { it.fontSizePrefs })
    }

    suspend fun updateFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.updateData { userData ->
            userData.copy(
                isFirstLaunch = isFirstLaunch,
            )
        }
    }

    suspend fun updateFontSizePrefs(fontSizePrefs: FontSizePrefs) {
        dataStore.updateData { userData ->
            userData.copy(
                fontSizePrefs = fontSizePrefs
            )
        }
    }
}
