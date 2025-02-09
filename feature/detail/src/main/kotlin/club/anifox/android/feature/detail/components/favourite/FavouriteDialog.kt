package club.anifox.android.feature.detail.components.favourite

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import club.anifox.android.core.uikit.component.button.AnifoxButtonSurface
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import club.anifox.android.feature.detail.R

@Composable
internal fun FavouriteDialog(
    setShowDialog: (Boolean) -> Unit,
    selectedFavouriteState: AnimeFavouriteStatus?,
    updateFavouriteStatus: (AnimeFavouriteStatus?) -> Unit,
) {
    Dialog(
        onDismissRequest = { setShowDialog.invoke(false) },
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.surfaceContainer,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.feature_detail_section_favourite_dialog_title),
                    style = MaterialTheme.typography.titleMedium,
                )

                Column(Modifier.selectableGroup()) {
                    AnimeFavouriteStatus.getAllStatuses().forEach { statusString ->
                        Row(
                            Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                                .selectable(
                                    selected = when {
                                        statusString == AnimeFavouriteStatus.NOT_WATCHING -> selectedFavouriteState == null
                                        else -> selectedFavouriteState?.toString() == statusString
                                    },
                                    onClick = {
                                        updateFavouriteStatus.invoke(
                                            AnimeFavouriteStatus.fromString(statusString)
                                        )
                                        setShowDialog.invoke(false)
                                    },
                                    role = Role.RadioButton,
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = when {
                                    statusString == AnimeFavouriteStatus.NOT_WATCHING -> selectedFavouriteState == null
                                    else -> selectedFavouriteState?.toString() == statusString
                                },
                                onClick = null,
                            )
                            Text(
                                text = statusString,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        }
                    }
                }

                AnifoxButtonSurface(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    paddingValues = PaddingValues(0.dp),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        setShowDialog(false)
                    },
                ) {
                    Text(
                        text = stringResource(R.string.feature_detail_section_favourite_dialog_close),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewFavouriteDialog() {
    DefaultPreview {
        FavouriteDialog(
            setShowDialog = { },
            selectedFavouriteState = null,
            updateFavouriteStatus = { },
        )
    }
}