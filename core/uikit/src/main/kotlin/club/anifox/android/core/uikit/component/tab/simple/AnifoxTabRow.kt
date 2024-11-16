package club.anifox.android.core.uikit.component.tab.simple

import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> AnifoxTabRow(
    modifier: Modifier = Modifier,
    items: List<T>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    itemToText: (T) -> String,
) {
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            AnifoxTab(
                text = itemToText(item),
                isSelected = index == selectedIndex,
                onClick = { onTabSelected(index) }
            )
        }
    }
}