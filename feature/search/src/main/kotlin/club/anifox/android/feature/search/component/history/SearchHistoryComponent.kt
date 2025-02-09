package club.anifox.android.feature.search.component.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.chip.AnifoxChipSurface
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnSurface
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.feature.search.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SearchHistoryComponent(
    searchHistory: List<String>,
    onHistoryItemClick: (String) -> Unit,
    onDeleteHistoryClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(R.string.feature_search_title_history),
            maxLines = 1,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            overflow = FlowRowOverflow.Clip,
        ) {
            searchHistory.forEach { searchQuery ->
                AnifoxChipSurface(
                    modifier = Modifier.clickableWithoutRipple {
                        onHistoryItemClick.invoke(searchQuery)
                    },
                    title = searchQuery,
                )
            }
        }

        Row(
            modifier = Modifier
                .clickableWithoutRipple {
                    onDeleteHistoryClick.invoke()
                }
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.feature_search_title_history_clear),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            AnifoxIconOnSurface(
                modifier = Modifier.size(16.dp),
                imageVector = Outlined.Delete,
                contentDescription = null,
            )
        }
    }
}