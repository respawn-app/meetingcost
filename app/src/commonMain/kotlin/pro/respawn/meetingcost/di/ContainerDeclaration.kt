@file:Suppress("INVISIBLE_REFERENCE")

package pro.respawn.meetingcost.di

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindings.BindingDI
import org.kodein.di.bindings.NoArgBindingDI
import org.kodein.di.multiton
import org.kodein.di.scoped
import org.kodein.di.singleton
import pro.respawn.flowmvi.api.Container
import kotlin.internal.LowPriorityInOverloadResolution

// to resolve the ambiguity
@Suppress("INVISIBLE_REFERENCE")
@LowPriorityInOverloadResolution
inline fun <reified T : Container<*, *, *>, reified P : Any> DI.Builder.container(
    @BuilderInference crossinline definition: BindingDI<DestinationContext>.(P) -> T
) = bind<T>() with scoped(DestinationScope).multiton { params: P ->
    definition(params).apply { store.start(context.coroutineScope) }
}

inline fun <reified T : Container<*, *, *>> DI.Builder.container(
    @BuilderInference crossinline definition: NoArgBindingDI<DestinationContext>.() -> T
) = bind<T>() with scoped(DestinationScope).singleton {
    definition().apply { store.start(context.coroutineScope) }
}
