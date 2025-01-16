package club.anifox.android.core.uikit.component.tab.simple

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> AnifoxScrollableTabRow(
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier,
    items: List<T>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    itemToText: @Composable (T) -> String,
    edgePadding: Int = 16,
    selectedColor: Color,
    unSelectedColor: Color,
) {
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
        edgePadding = edgePadding.dp
    ) {
        items.forEachIndexed { index, item ->
            AnifoxTab(
                modifier = itemModifier,
                text = itemToText(item),
                isSelected = index == selectedIndex,
                onClick = { onTabSelected(index) },
                selectedColor = selectedColor,
                unselectedColor = unSelectedColor,
            )
        }
    }
}