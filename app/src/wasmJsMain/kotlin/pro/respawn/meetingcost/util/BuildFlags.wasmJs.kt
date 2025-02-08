package pro.respawn.meetingcost.util

import pro.respawn.meetingcost.BuildFlags

// TODO: Try to properly resolve?
internal actual val BuildFlags.debuggable: Boolean get() = true
internal actual val BuildFlags.platform: Platform get() = Platform.Web
