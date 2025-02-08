package pro.respawn.meetingcost.util

import pro.respawn.meetingcost.BuildFlags

actual val BuildFlags.platform: Platform get() = Platform.Desktop
actual val BuildFlags.debuggable: Boolean by lazy { System.getenv("DEBUG")?.toBooleanStrictOrNull() == true }
