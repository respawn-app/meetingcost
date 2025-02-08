package pro.respawn.meetingcost.di

import androidx.compose.runtime.Composable
import org.kodein.di.LazyDelegate
import org.kodein.di.compose.rememberInstance
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState

@Composable
inline fun <reified T : Container<S, I, A>, S : MVIState, I : MVIIntent, A : MVIAction> container(): T {
    val value by rememberInstance<T>()
    return value
}

@Composable
inline fun <reified T : Container<S, I, A>, reified P : Any, S : MVIState, I : MVIIntent, A : MVIAction> container(
    noinline params: () -> P,
): LazyDelegate<T> = rememberInstance<P, T>(null, fArg = params)

@Composable
inline fun <reified T : Container<S, I, A>, reified P : Any, S : MVIState, I : MVIIntent, A : MVIAction> container(
    params: P,
): T {
    val value by rememberInstance<P, T>(null, arg = params)
    return value
}
