package pro.respawn.meetingcost.di.modules

import org.kodein.di.DI
import org.kodein.di.bindProvider
import pro.respawn.meetingcost.CostCounterContainer

val appModule by DI.Module {
    importAll(
        platformAppModule,
        commonArchModule,
    )
    bindProvider { CostCounterContainer() }
}
