package pro.respawn.meetingcost.system

import androidx.compose.runtime.Composable
import kotlinx.io.files.Path

@Composable
actual fun rememberFileManager(): FileManager = WebFileManager

private object WebFileManager : FileManager {

    // we don't have access to the file system, so these will just be at the root of the local storage.
    override suspend fun filesDir(): Path = kotlinx.io.files.Path("")
    override suspend fun cacheDir(): Path = kotlinx.io.files.Path("")
}
