package pro.respawn.meetingcost.util

import kotlin.time.Duration

fun Duration.formatAsTime(withSeconds: Boolean = false) = toComponents { hours, minutes, seconds, _ ->
    buildString {
        append(hours.toString().padStart(2, '0'))
        append(':')
        append(minutes.toString().padStart(2, '0'))
        if (seconds != 0 || withSeconds) {
            append(':')
            append(seconds.toString().padStart(2, '0'))
        }
    }
}
