package com.daniilvarezhkin.bluetoothstudio.presentation.views.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.TabItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacteristicTabRow(
    selectedTab: TabItem,
    onSelectTab: (TabItem) -> Unit,
    tabItems: List<TabItem>,
    modifier: Modifier = Modifier
) {
    if (tabItems.isEmpty()) return

    SingleChoiceSegmentedButtonRow(
        modifier = modifier.fillMaxWidth()
    ) {
        tabItems.forEachIndexed { index, tab ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = tabItems.size
                ),
                selected = selectedTab == tab,
                onClick = { onSelectTab(tab) },
                label = {
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                },
                icon = { },
            )
        }
    }
}

@Composable
@Preview(showBackground = true, widthDp = 400)
fun CharacteristicTabRowPreview() {
    MaterialTheme {
        CharacteristicTabRow(
            selectedTab = TabItem.READ,
            onSelectTab = {},
            tabItems = listOf(TabItem.READ, TabItem.WRITE, TabItem.NOTIFY),
            modifier = Modifier.padding(16.dp)
        )
    }
}