package pro.respawn.meetingcost.util

import pro.respawn.kmmutils.inputforms.Form
import pro.respawn.kmmutils.inputforms.Rule
import pro.respawn.kmmutils.inputforms.ValidationError
import pro.respawn.kmmutils.inputforms.ValidationStrategy
import pro.respawn.kmmutils.inputforms.default.Rules
import pro.respawn.kmmutils.inputforms.dsl.checks

private val IsPositive = Rule { { it.toIntOrNull().let { it != null && it > 0 } } checks { InvalidRate(it) } }

val MemberCountForm = Form(
    ValidationStrategy.FailFast,
    Rules.DigitsOnly,
    Rules.NonEmpty,
    Rules.LengthInRange(1..2),
    IsPositive,
)

val HourlyRateForm = Form(
    ValidationStrategy.FailFast,
    Rules.DigitsOnly,
    Rules.NonEmpty,
    Rules.LengthInRange(1..3),
    IsPositive,
)

val ClientNameForm = Form(
    ValidationStrategy.FailFast,
    Rules.ShorterThan(40)
)

class InvalidRate(override val value: String) : ValidationError.Generic(value)
