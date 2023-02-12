package de.grossmax.portfolioandroid.app.userinterface.screens.timer

fun Int?.asStringWithFixedLength(length: Int): String {
    return this?.toString()?.padStart(length, '0') ?: "0".repeat(length)
}

fun Int.asFullHours() = this / 3600
fun Int.asRemainingMinutes() = (this % 3600) / 60
fun Int.asRemainingSeconds() = this % 60