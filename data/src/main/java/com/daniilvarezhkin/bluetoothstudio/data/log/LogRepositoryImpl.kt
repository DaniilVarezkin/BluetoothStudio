package com.daniilvarezhkin.bluetoothstudio.data.log

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.log.BLog
import com.daniilvarezhkin.bluetoothstudio.domain.log.BLogMetadata
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogRepository
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogTag
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber
import java.util.concurrent.atomic.AtomicLong

class LogRepositoryImpl : LogRepository {
    private object LogIdGenerator {
        private val counter = AtomicLong(0)
        fun getNext() = counter.incrementAndGet()
    }

    private val _logs = MutableSharedFlow<BLog>(
        replay = 50,
        extraBufferCapacity = 100,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val logs = _logs.asSharedFlow()


    override fun addBLog(priority: Int, tag: LogTag, message: String, address: String?, deviceName: String?, charKey: CharacteristicKey?, rawData: ByteArray?) {
        Timber.log(priority, tag.name, message)
        _logs.tryEmit(
            BLog(
                id = LogIdGenerator.getNext(),
                timestamp = System.currentTimeMillis(),
                priority = priority,
                tag = tag,
                message = message,
                address = address,
                metadata = BLogMetadata(
                    deviceName = deviceName,
                    charKey = charKey,
                    rawData = rawData
                ),
            )
        )
    }
}