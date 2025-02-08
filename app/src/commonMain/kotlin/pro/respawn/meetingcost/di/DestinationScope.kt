package pro.respawn.meetingcost.di

import org.kodein.di.bindings.Scope

@PublishedApi
internal object DestinationScope : Scope<DestinationContext> {

    override fun getRegistry(context: DestinationContext) = context.registry
}
