package pro.respawn.meetingcost.di

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.bindings.ScopeRegistry
import pro.respawn.flowmvi.api.SubscriberLifecycle

interface DestinationContext {

    val coroutineScope: CoroutineScope
    val lifecycle: SubscriberLifecycle
    val registry: ScopeRegistry
}
