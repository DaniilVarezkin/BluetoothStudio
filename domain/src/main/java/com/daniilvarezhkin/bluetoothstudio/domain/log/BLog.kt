package com.daniilvarezhkin.bluetoothstudio.domain.log


data class BLog(
    val id: Long,
    val timestamp: Long,
    val priority: Int,
    val tag: LogTag,
    val message: String,
    val address: String?,
    val metadata: BLogMetadata? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BLog

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
