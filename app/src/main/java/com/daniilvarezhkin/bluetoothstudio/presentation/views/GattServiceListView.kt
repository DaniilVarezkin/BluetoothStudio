package com.daniilvarezhkin.bluetoothstudio.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.CharacteristicProperty
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import com.daniilvarezhkin.bluetoothstudio.presentation.components.ExpandableCard
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.Typography
import kotlin.Unit

@Composable
fun GattServiceListView(
    services: List<GattService>,
    onClickCharacteristic: (GattCharacteristic) -> Unit,
    onClickReadCharacteristic: (GattCharacteristic) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(services, { it.uuid }) { service ->
            GattServiceItemView(
                service = service,
                onClickRead = onClickReadCharacteristic,
                onClickCharacteristic = onClickCharacteristic,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun GattServiceTitleView(
    service: GattService,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Service",
            style = Typography.labelSmall
        )
        Text(
            text = service.name ?: stringResource(R.string.custom_service),
            style = Typography.titleMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = "UUID: ${service.shortUuid ?: service.uuid.toString().uppercase()}",
            style = Typography.bodyMedium
        )
    }
}

@Composable
fun GattCharacteristicView(
    characteristic: GattCharacteristic,
    onClickRead: (GattCharacteristic) -> Unit,
    modifier: Modifier = Modifier
) {
    val characteristicValue by characteristic.value.collectAsState(null)
    Column(modifier = modifier) {
        Text(
            text = characteristic.name ?: stringResource(R.string.custom_characteristic),
            style = Typography.titleMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = "UUID: ${characteristic.shortUuid ?: characteristic.uuid.toString().uppercase()}",
            style = Typography.bodyMedium
        )
        Row {
            characteristic.properties.forEach {
                Text(it.name)
                Spacer(Modifier.width(10.dp))
            }
        }
        Text(
            text = "Value: ${characteristicValue?.decodeToString()}",
            style = Typography.bodyMedium
        )
        if(characteristic.properties.contains(CharacteristicProperty.READ)) {
            Button(onClick = { onClickRead(characteristic) }) {
                Text("Прочитать")
            }
        }
    }
}

@Composable
fun GattServiceItemView(
    service: GattService,
    onClickRead: (GattCharacteristic) -> Unit,
    onClickCharacteristic: (GattCharacteristic) -> Unit,
    modifier: Modifier = Modifier
) {
    ExpandableCard(
        modifier = modifier,
        title = {
            GattServiceTitleView(
                service,
                Modifier.padding(10.dp)
            )
        }
    ) {
        HorizontalDivider()
        Column {
            service.characteristics.forEach { characteristic ->
                GattCharacteristicView(
                    characteristic = characteristic,
                    onClickRead = onClickRead,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable(onClick = { onClickCharacteristic(characteristic) })
                )
            }
        }
    }
}