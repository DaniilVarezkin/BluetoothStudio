package com.daniilvarezhkin.bluetoothstudio.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import com.daniilvarezhkin.bluetoothstudio.presentation.components.ExpandableCard

@Composable
fun GattServiceListView(
    services: List<GattService>,
    modifier: Modifier = Modifier
) {
    println(services.toString())
    LazyColumn(
        modifier = modifier
    ) {
        items(services, {it.uuid}) { service ->
            GattServiceItemView(
                service = service,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun GattServiceItemView(
    service: GattService,
    modifier: Modifier = Modifier
) {
    ExpandableCard(
        modifier = modifier,
        title = {
            Text(text = service.uuid.toString())
        }
    ) {
        HorizontalDivider()
        Column {
            service.characteristics.forEach { characteristic ->
                Text(text = characteristic.uuid.toString())
            }
            HorizontalDivider()
        }
    }
}