package pro.respawn.meetingcost.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pro.respawn.kmmutils.compose.bringIntoViewOnFocus
import pro.respawn.kmmutils.compose.modifier.thenIfNotNull
import pro.respawn.kmmutils.inputforms.Input
import pro.respawn.kmmutils.inputforms.Input.Invalid
import pro.respawn.kmmutils.inputforms.dsl.isEmptyValue
import pro.respawn.kmmutils.inputforms.dsl.isInvalid
import pro.respawn.meetingcost.ui.icons.Icons
import pro.respawn.meetingcost.ui.icons.Plus
import pro.respawn.meetingcost.ui.theme.Opacity
import pro.respawn.meetingcost.ui.theme.Size

private const val AutoFocusDelay = 200L

@Composable
fun RTextInput(
    input: Input,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    labelStyle: TextStyle = MaterialTheme.typography.labelMedium,
    /**
     * Pass non-null if the keyboard should be shown when entering composition or when the property changes
     * Only ONE field per screen should have this as true!
     */
    focusRequester: FocusRequester? = null,
    lengthRange: IntRange? = null,
    label: String? = null,
    placeholder: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions.default(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = {
        ClearFieldIcon(
            input = input,
            onClear = { onTextChange("") }
        )
    },
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
) = RTextInput(
    input = input,
    modifier = modifier,
    hint = hint,
    labelStyle = labelStyle,
    focusRequester = focusRequester
) { isFocused ->
    OutlinedTextFieldContent(
        input = input,
        onValueChange = onTextChange,
        labelStyle = labelStyle,
        isFocused = isFocused,
        lengthRange = lengthRange,
        label = label,
        placeholder = placeholder,
        singleLine = singleLine,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = colors
    )
}

@Composable
fun RTextInput(
    input: Input,
    modifier: Modifier = Modifier,
    hint: String? = null,
    labelStyle: TextStyle = MaterialTheme.typography.labelMedium,
    /**
     * Pass non-null if the keyboard should be shown when entering composition or when the property changes
     * Only ONE field per screen should have this as true!
     */
    focusRequester: FocusRequester? = null,
    textField: @Composable (isFocused: Boolean) -> Unit,
) {
    LaunchedEffect(focusRequester) {
        if (focusRequester == null) return@LaunchedEffect
        delay(AutoFocusDelay)
        // only focus when field is empty, may indicate that the person wants to make other changes first
        if (input.isEmptyValue) focusRequester.requestFocus()
    }
    var isFocused by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .bringIntoViewOnFocus()
            .thenIfNotNull(focusRequester) { focusRequester(it) }
            .onFocusChanged { isFocused = it.isFocused }
    ) {
        textField(isFocused)
        AnimatedVisibility(
            visible = hint != null || input.isInvalid,
            modifier = Modifier
                .animateContentSize()
                .padding(vertical = 8.dp),
        ) {
            when {
                input is Invalid -> Text(
                    text = input.errors.mapNotNull { it::class.simpleName }.joinToString("\n"),
                    style = labelStyle,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Start,
                )
                hint != null -> Text(
                    text = hint,
                    style = labelStyle,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}

@Composable
fun ClearFieldIcon(
    input: Input,
    onClear: () -> Unit,
    enabled: Boolean = true,
    hideKeyboard: Boolean = true,
) = AnimatedVisibility(visible = !input.isEmptyValue) {
    val focusManager = LocalFocusManager.current
    RIcon(
        icon = Icons.Plus,
        onClick = {
            focusManager.clearFocus(hideKeyboard)
            onClear()
        },
        modifier = Modifier.requiredSize(Size.icon).rotate(degrees = 45f),
        enabled = !input.isEmptyValue && enabled,
    )
}

@Composable
private fun LabelWithCounter(
    lengthRange: IntRange?,
    label: String?,
    inputLength: Int,
    textStyle: TextStyle,
    isFocused: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.animateContentSize()
    ) {
        AnimatedVisibility(
            visible = label != null,
            modifier = Modifier.weight(1f, fill = false),
        ) {
            Crossfade(label) {
                Text(
                    text = it ?: return@Crossfade,
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        AnimatedVisibility(lengthRange != null && isFocused) {
            if (lengthRange == null) return@AnimatedVisibility
            val color = if (inputLength > lengthRange.last)
                MaterialTheme.colorScheme.error
            else
                LocalContentColor.current
            Text(
                text = "($inputLength/${lengthRange.last})",
                color = color,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                style = textStyle,
                modifier = Modifier.padding(start = 4.dp),
            )
        }
    }
}

@Composable
fun OutlinedTextFieldContent(
    input: Input,
    onValueChange: (String) -> Unit,
    labelStyle: TextStyle,
    isFocused: Boolean,
    lengthRange: IntRange? = null,
    label: String? = null,
    placeholder: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions.default(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
) {
    OutlinedTextField(
        value = input.value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        label = { LabelWithCounter(lengthRange, label, input.value.length, labelStyle, isFocused) },
        placeholder = placeholder?.let { value ->
            {
                Crossfade(targetState = value) {
                    Text(
                        text = it,
                        style = textStyle,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                        modifier = Modifier.alpha(Opacity.disabled)
                    )
                }
            }
        },
        singleLine = singleLine,
        isError = input is Invalid,
        readOnly = readOnly,
        colors = colors,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        minLines = minLines,
        shape = MaterialTheme.shapes.large,
    )
}

/**
 * @param onOther an actions to be run when you specify one that is not the one that can be handled by focusManager
 *   e.g. Go, Search, and Send. By default does nothing.
 */
@Composable
fun KeyboardActions.Companion.default(
    onOther: (FocusManager.() -> Unit)? = null,
) = LocalFocusManager.current.run {
    remember {
        KeyboardActions(
            onDone = { clearFocus() },
            onNext = { moveFocus(FocusDirection.Next) },
            onPrevious = { moveFocus(FocusDirection.Previous) },
            onGo = onOther?.let { { it() } },
            onSearch = onOther?.let { { it() } },
            onSend = onOther?.let { { it() } },
        )
    }
}
