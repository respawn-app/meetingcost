package pro.respawn.meetingcost.analytics

import pro.respawn.flowmvi.api.FlowMVIDSL
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.dsl.StoreBuilder
import pro.respawn.flowmvi.dsl.lazyPlugin

interface AnalyticsEvent {

    val event: String? get() = null
    val params: Map<String, Any?> get() = emptyMap()
}

/**
 * Plugin that intercepts and sends analytics events for any actions in the store that implement [AnalyticsEvent]
 **/
@FlowMVIDSL
fun <S : MVIState, I : MVIIntent, A : MVIAction> analyticsPlugin(
    name: String? = "AnalyticsPlugin",
    analytics: Analytics = LogAnalytics,
) = lazyPlugin<S, I, A> {
    this.name = name
    onStart {
        analytics.openedScreen(config.name ?: return@onStart)
    }
    onSubscribe { subs ->
        if (subs == 1) analytics.event("ScreenVisible", config.name)
    }
    onUnsubscribe { subs ->
        if (subs == 0) analytics.event("ScreenHidden", config.name)
    }
    onIntent { intent ->
        if (intent is AnalyticsEvent) analytics.event(
            name = intent.event ?: return@onIntent intent,
            screen = config.name,
            params = intent.params,
        )
        intent
    }
    onAction { action ->
        if (action is AnalyticsEvent) analytics.event(
            name = action.event ?: return@onAction action,
            screen = config.name,
            params = action.params,
        )
        action
    }
    onException { e ->
        analytics.error(e)
        e
    }
    onStop {
        analytics.event("ScreenExit", config.name)
    }
}

/**
 * Install a new [analyticsPlugin].
 **/
@FlowMVIDSL
fun <S : MVIState, I : MVIIntent, A : MVIAction> StoreBuilder<S, I, A>.analytics(
    name: String? = "AnalyticsPlugin",
) = install(analyticsPlugin(name))
