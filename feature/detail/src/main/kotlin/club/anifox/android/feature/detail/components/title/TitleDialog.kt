package club.anifox.android.feature.detail.components.title

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import club.anifox.android.commonui.component.button.AnifoxButtonSurface
import club.anifox.android.feature.detail.R

@Composable
internal fun TitleDialog(
    title: String,
    engTitle: String,
    japaneseTitle: String,
    synonymsTitle: String,
    setShowDialog: (Boolean) -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.surfaceContainer,
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                val titleItems = mutableListOf<@Composable () -> Unit>()

                titleItems.add {
                    Column {
                        Text(
                            text = stringResource(R.string.feature_detail_dialog_title),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
                if(engTitle.isNotEmpty()) {
                    titleItems.add {
                        Column {
                            Text(
                                text = stringResource(R.string.feature_detail_dialog_original_title),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = engTitle,
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                    }
                }
                if(japaneseTitle.isNotEmpty()) {
                    titleItems.add {
                        Column {
                            Text(
                                text = stringResource(R.string.feature_detail_dialog_japanese_title),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = japaneseTitle,
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                    }
                }
                if(synonymsTitle.isNotEmpty()) {
                    titleItems.add {
                        Column {
                            Text(
                                text = stringResource(R.string.feature_detail_dialog_synonyms_title),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = synonymsTitle,
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                    }
                }

                titleItems.forEachIndexed { index, item ->
                    item()
                    if (index < titleItems.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                        )
                    }
                }

                AnifoxButtonSurface(
                    modifier = Modifier.fillMaxWidth(),
                    paddingValues = PaddingValues(0.dp),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        setShowDialog(false)
                    },
                ) {
                    Text(
                        text = stringResource(R.string.feature_detail_dialog_button_close_title),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}