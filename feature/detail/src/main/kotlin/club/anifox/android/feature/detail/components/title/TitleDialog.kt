package club.anifox.android.feature.detail.components.title

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import club.anifox.android.core.uikit.component.button.AnifoxButtonSurface
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnSurface
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.feature.detail.R

@Composable
internal fun TitleDialog(
    title: String,
    engTitle: String,
    japaneseTitle: String,
    synonymsTitle: String,
    setShowDialog: (Boolean) -> Unit,
) {
    val clipboardManager = LocalClipboardManager.current
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
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(R.string.feature_detail_dialog_title),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            AnifoxIconOnSurface(
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 4.dp)
                                    .clickable {
                                        clipboardManager.setText(buildAnnotatedString { append(title) })
                                    },
                                Filled.ContentCopy,
                            )
                        }
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
                if(engTitle.isNotEmpty() && engTitle != "null") {
                    titleItems.add {
                        Column {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = stringResource(R.string.feature_detail_dialog_original_title),
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                AnifoxIconOnSurface(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 4.dp)
                                        .clickable {
                                            clipboardManager.setText(buildAnnotatedString { append(engTitle) })
                                        },
                                    Filled.ContentCopy,
                                )
                            }
                            Text(
                                text = engTitle,
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                    }
                }
                if(japaneseTitle.isNotEmpty() && japaneseTitle != "null") {
                    titleItems.add {
                        Column {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = stringResource(R.string.feature_detail_dialog_japanese_title),
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                AnifoxIconOnSurface(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 4.dp)
                                        .clickable {
                                            clipboardManager.setText(buildAnnotatedString { append(japaneseTitle) })
                                        },
                                    Filled.ContentCopy,
                                )
                            }
                            Text(
                                text = japaneseTitle,
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                    }
                }
                if(synonymsTitle.isNotEmpty() && synonymsTitle != "null") {
                    titleItems.add {
                        Column {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = stringResource(R.string.feature_detail_dialog_synonyms_title),
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                AnifoxIconOnSurface(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 4.dp)
                                        .clickable {
                                            clipboardManager.setText(buildAnnotatedString { append(synonymsTitle) })
                                        },
                                    Filled.ContentCopy,
                                )
                            }
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

@PreviewLightDark
@Composable
private fun PreviewTitleDialog() {
    DefaultPreview {
        TitleDialog(
            title = "Title",
            japaneseTitle = "JapaneseTitle",
            synonymsTitle = "SynonymsTitle",
            engTitle = "EngTitle",
            setShowDialog = {},
        )
    }
}