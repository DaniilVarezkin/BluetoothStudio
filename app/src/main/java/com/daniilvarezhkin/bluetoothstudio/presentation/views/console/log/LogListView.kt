package com.daniilvarezhkin.bluetoothstudio.presentation.views.console.log

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.daniilvarezhkin.bluetoothstudio.domain.log.BLog

@Composable
fun LogListView(
    BLogs: List<BLog>,
    showTimestamp: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(BLogs, key = { it.id }) { log ->
            BLogItemView(
                log = log,
                showTimestamp = showTimestamp
            )
        }
    }
}