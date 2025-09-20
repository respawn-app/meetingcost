package pro.respawn.meetingcost.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.PlayArrow: ImageVector
    get() {
        if (_PlayArrow != null) {
            return _PlayArrow!!
        }
        _PlayArrow = ImageVector.Builder(
            name = "PlayArrow",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF292D32)),
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
            ) {
                moveTo(6f, 12f)
                verticalLineTo(9.33f)
                curveTo(6f, 6.02f, 8.35f, 4.66f, 11.22f, 6.32f)
                lineTo(13.53f, 7.66f)
                lineTo(15.84f, 9f)
                curveTo(18.71f, 10.66f, 18.71f, 13.37f, 15.84f, 15.03f)
                lineTo(13.53f, 16.37f)
                lineTo(11.22f, 17.71f)
                curveTo(8.35f, 19.34f, 6f, 17.99f, 6f, 14.67f)
                verticalLineTo(12f)
                close()
            }
        }.build()

        return _PlayArrow!!
    }

@Suppress("ObjectPropertyName")
private var _PlayArrow: ImageVector? = null
