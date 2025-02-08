package pro.respawn.meetingcost.ui.widgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import pro.respawn.meetingcost.ui.theme.RespawnTheme
import kotlin.math.absoluteValue
import kotlin.random.Random

internal fun AnimatedContentTransitionScope<Int>.slidingIntSpec(targetState: Int, initialState: Int): ContentTransform =
    if (targetState > initialState) {
        //  If the target number is larger, it slides up and fades in
        //  while the initial (smaller) number slides up and fades out.
        slideInVertically { height -> height } + fadeIn() togetherWith
            slideOutVertically { height -> -height } + fadeOut()
    } else {
        //  If the target number is smaller, it slides down and fades in
        //  while the initial number slides down and fades out.
        slideInVertically { height -> -height } + fadeIn() togetherWith
            slideOutVertically { height -> height } + fadeOut()
    } using SizeTransform(false)
//  Disable clipping since the faded slide-in/out should
//  be displayed out of bounds.

@Composable
private fun Digit(
    digit: Int,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    color: Color = LocalContentColor.current
) {
    Text(
        modifier = modifier,
        text = digit.coerceIn(0..9).toString(),
        style = textStyle,
        color = color,
        softWrap = false,
        maxLines = 1,
    )
}

@Composable
fun SlidingDigit(
    digit: Int,
    textStyle: TextStyle = LocalTextStyle.current,
    color: Color = LocalContentColor.current
) {
    AnimatedContent(
        targetState = digit,
        transitionSpec = { slidingIntSpec(targetState, initialState) }
    ) { i ->
        Digit(digit = i, textStyle = textStyle, color = color)
    }
}

@Composable
fun SlidingNumber(
    number: Int,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    color: Color = LocalContentColor.current,
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center,
    modifier = modifier,
) {
    number.toDigits().forEach { i ->
        SlidingDigit(
            digit = i,
            textStyle = textStyle,
            color = color
        )
    }
}

// @Preview
@Composable
private fun SlidingNumberPreivew() = RespawnTheme {
    var number by remember { mutableIntStateOf(Random.nextInt(1)) }
    SlidingNumber(
        number = number,
        modifier = Modifier
            .clickable { number = Random.nextInt(1000) }
            .padding(48.dp)
    )
    Text(number.toString())
}

// TODO: Move to kmputils
fun Int.toDigits(base: Int = 10): List<Int> = sequence {
    var n = absoluteValue
    do {
        yield(n % base)
        n /= base
    } while (n != 0)
}
    .asIterable()
    .reversed()
