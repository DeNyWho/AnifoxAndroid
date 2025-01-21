package club.anifox.android.feature.detail.components.favourite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.chip.AnifoxChipSurfaceSelectable
import club.anifox.android.core.uikit.component.icon.AnifoxIcon
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import club.anifox.android.feature.detail.R

@Composable
internal fun FavouriteComponent(
    modifier: Modifier = Modifier,
    selectedFavouriteState: AnimeFavouriteStatus?,
    isInFavouriteState: Boolean,
    onUpdateFavouriteStatus: (AnimeFavouriteStatus?) -> Unit,
    onUpdateIsInFavourite: () -> Unit,
) {
    val showDialog = remember { mutableStateOf(false) }
    val currentStatus = selectedFavouriteState?.toString() ?: AnimeFavouriteStatus.NOT_WATCHING

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AnifoxChipSurfaceSelectable(
            modifier = Modifier
                .clickableWithoutRipple {
                    showDialog.value = true
                },
            title = currentStatus,
            surfaceShape = MaterialTheme.shapes.small,
            selected = selectedFavouriteState != null,
            selectedColor = MaterialTheme.colorScheme.primary,
            textStyle = MaterialTheme.typography.labelLarge,
            icon = {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Filled.KeyboardArrowDown,
                    contentDescription = null,
                )
            }
        )

        AnifoxChipSurfaceSelectable(
            modifier = Modifier
                .clickableWithoutRipple {
                    onUpdateIsInFavourite.invoke()
                },
            title = stringResource(R.string.feature_detail_section_favourite_chip_title),
            surfaceShape = MaterialTheme.shapes.small,
            selected = isInFavouriteState,
            selectedColor = MaterialTheme.colorScheme.primary,
            textStyle = MaterialTheme.typography.labelLarge,
            icon = {
                AnifoxIcon(
                    painter = painterResource(R.drawable.feature_detail_bookmark),
                    contentDescription = null,
                )
            }
        )

        if (showDialog.value) {
            FavouriteDialog(
                setShowDialog = {
                    showDialog.value = it
                },
                selectedFavouriteState = selectedFavouriteState,
                updateFavouriteStatus = onUpdateFavouriteStatus
            )
        }
    }
}