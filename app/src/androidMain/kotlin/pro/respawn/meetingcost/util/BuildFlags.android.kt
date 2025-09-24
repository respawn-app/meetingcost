package pro.respawn.meetingcost.util

import pro.respawn.meetingcost.BuildFlags

actual val BuildFlags.debuggable: Boolean get() = true
actual val BuildFlags.platform: Platform get() = Platform.Android