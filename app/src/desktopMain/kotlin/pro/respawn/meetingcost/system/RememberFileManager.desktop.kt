package pro.respawn.meetingcost.system

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.runtime.Composable
import kotlinx.io.files.Path

/**
 * Not ideal, for demonstration purposes only
 */
object DesktopFileManager : FileManager {

    override suspend fun cacheDir(): Path = Path("./.cache")

    override suspend fun filesDir(): Path = Path("./files")
}

@Composable
actual fun rememberFileManager(): FileManager = DesktopFileManager
