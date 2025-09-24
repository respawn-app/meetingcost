package pro.respawn.meetingcost

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware

class MeetingCostApplication : Application(), DIAware {

    override val di: DI = DI {

    }

    override fun onCreate() {
        super.onCreate()
    }
}