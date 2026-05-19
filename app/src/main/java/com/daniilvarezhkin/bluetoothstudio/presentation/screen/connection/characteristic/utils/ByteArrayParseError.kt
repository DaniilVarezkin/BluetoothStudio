package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils

import com.daniilvarezhkin.core.RootError

sealed interface ByteArrayParseError : RootError {
    data object OddHexLength : ByteArrayParseError
    data object InvalidHexCharacters : ByteArrayParseError
}