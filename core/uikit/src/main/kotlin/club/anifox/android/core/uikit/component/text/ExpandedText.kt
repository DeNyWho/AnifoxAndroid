package club.anifox.android.core.uikit.component.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnBackground

@Composable
fun ExpandedText(
    modifier: Modifier = Modifier,
    text: String,
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        val textLayoutResultHolder = arrayOfNulls<TextLayoutResult>(1)

        subcompose("measuring") {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                onTextLayout = { textLayoutResult ->
                    textLayoutResultHolder[0] = textLayoutResult
                }
            )
        }.map { it.measure(constraints) }.first()

        val textLayoutResult = textLayoutResultHolder[0]
        val isTextExceedingMaxLines = (textLayoutResult?.lineCount ?: 0) > 5

        val mainContent = if (isTextExceedingMaxLines) {
            subcompose("expandableContent") {
                if (isExpanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onExpandedChanged(false) },
                            ),
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Justify,
                        )

                        AnifoxIconOnBackground(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterHorizontally),
                            imageVector = Filled.KeyboardArrowUp,
                            contentDescription = "Switch",
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onExpandedChanged(true) },
                            ),
                    ) {
                        Text(
                            text = text,
                            maxLines = 5,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Justify,
                        )

                        AnifoxIconOnBackground(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterHorizontally),
                            imageVector = Filled.KeyboardArrowDown,
                            contentDescription = "Expand",
                        )
                    }
                }
            }
        } else {
            subcompose("simpleText") {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Justify,
                )
            }
        }

        val mainPlaceable = mainContent.map { it.measure(constraints) }.first()
        layout(mainPlaceable.width, mainPlaceable.height) {
            mainPlaceable.place(0, 0)
        }
    }
}