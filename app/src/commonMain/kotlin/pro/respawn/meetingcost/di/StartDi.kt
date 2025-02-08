package pro.respawn.meetingcost.di

import org.kodein.di.DI
import org.kodein.di.conf.global
import pro.respawn.meetingcost.di.modules.appModule

internal inline fun initializeDi(
    vararg modules: DI.Module,
    crossinline configure: DI.Builder.() -> Unit = { }
) = DI.global.addConfig {
    import(appModule)
    importAll(modules = modules)
    configure()
}
