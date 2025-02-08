package pro.respawn.meetingcost.util

import kotlin.jvm.JvmInline

/**
 * Optional (nullable) value for use in places that do not support nullable type parameters, such as DI [pro.respawn.meetingcost.di.container]
 */
@JvmInline
value class Optional<T : Any>(val value: T?) {

    fun isEmpty() = value == null
    fun orThrow() = value ?: throw IllegalArgumentException("Optional value is null")
}
