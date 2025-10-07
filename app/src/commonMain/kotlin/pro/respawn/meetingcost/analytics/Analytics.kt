package pro.respawn.meetingcost.analytics

import pro.respawn.flowmvi.logging.PlatformStoreLogger
import pro.respawn.flowmvi.logging.StoreLogLevel
import pro.respawn.flowmvi.logging.error

interface Analytics {

    fun event(name: String, screen: String?, params: Map<String, Any?> = emptyMap())
    fun error(e: Exception)
    fun openedScreen(name: String)
}

object LogAnalytics : Analytics {

    private val logger = PlatformStoreLogger
    private const val Tag = "Analytics"

    override fun event(name: String, screen: String?, params: Map<String, Any?>) {
        logger.log(StoreLogLevel.Info, Tag) {
            "Event: $name, screen: $screen, params: $params"
        }
    }

    override fun error(e: Exception) = logger.error(Tag) {
        "Error: ${e.stackTraceToString()}"
    }

    override fun openedScreen(name: String) {
        logger.log(StoreLogLevel.Info, Tag) { "Opened screen: $name" }
    }
}
