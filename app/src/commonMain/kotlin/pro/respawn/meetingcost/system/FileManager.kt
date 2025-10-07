package pro.respawn.meetingcost.system

import kotlinx.io.files.Path

interface FileManager {

    suspend fun cacheDir(): Path
    suspend fun filesDir(): Path
}
