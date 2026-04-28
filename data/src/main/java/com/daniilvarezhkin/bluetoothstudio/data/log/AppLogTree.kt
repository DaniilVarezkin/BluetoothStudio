package com.daniilvarezhkin.bluetoothstudio.data.log

import com.daniilvarezhkin.bluetoothstudio.domain.log.LogTag
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogRepository
import timber.log.Timber

class AppLogTree(
    private val logRepository: LogRepository
) : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//        if (tag != null && tag in LogTag.all) {
//            logRepository.addBLog(
//                priority = priority,
//                tag = tag,
//                message = message,
//                address = "",
//                rawData = null
//            )
//        }
    }
}