package pro.respawn.meetingcost.system

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.files.Path
import pro.respawn.kmmutils.coroutines.suspendLazy

@Composable
actual fun rememberFileManager(): FileManager {
    val ctx = LocalContext.current
    return remember { AndroidFileManager(ctx.applicationContext) }
}

private class AndroidFileManager(context: Context) : FileManager {

    val filesDir = suspendLazy {
        withContext(Dispatchers.IO) { Path(context.filesDir.path) }
    }
    val cacheDir = suspendLazy {
        withContext(Dispatchers.IO) { Path(context.cacheDir.path) }
    }

    override suspend fun filesDir() = filesDir.invoke()
    override suspend fun cacheDir() = cacheDir.invoke()
}
