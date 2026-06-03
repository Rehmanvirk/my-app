package com.app.calllogs.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.calllogs.di.data.FieldValue
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.collections.mapValues

// Define custom colors
val DarkBackground = Color.White
val ButtonBlue = Color(0xFF1677FF)
val TextColor = Color(0xFF060B10)
val OrangePriority = Color(0xFFFF8C00)
val GreenStatus = Color(0xFF2BB769)

val CardColorD = Color(0XFF1C2631)

val CardColor =  Color(0xFFE8E9EB)

val TextGreyColor = Color(0xFF5E7285)

var relatedToModule = ""
var relatedToRecordId = ""
var relatedToZohoId = ""

// Define custom typography
val typography = Typography(
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White),
    headlineSmall = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = TextColor),
    bodySmall = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp, color = TextColor),
//    bu = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
)

val bg = Brush.verticalGradient(
    colors = listOf(Color(0xFF0B1722), Color(0xFF08101A), Color(0xFF060B10))
)

// Helper to determine Stage Colors
fun getStageColor(stage: String): Pair<Color, Color> {
    return when (stage) {
        "Negotiation" -> Color(0xFFFEF3C7) to Color(0xFFD97706) // Yellow bg, Orange text
        "Proposal" -> Color(0xFFDBEAFE) to Color(0xFF2563EB)    // Light Blue bg, Blue text
        "Closing" -> Color(0xFFDCFCE7) to Color(0xFF059669)     // Light Green bg, Green text
        "Discovery" -> Color(0xFFF3F4F6) to Color(0xFF4B5563)   // Light Gray bg, Gray text
        else -> Color(0xFFF3F4F6) to Color(0xFF4B5563)
    }
}

fun Double.formatAsUSDCurrency(): String {
    // Specify the locale explicitly if needed
    val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    return format.format(this)
}

fun formatDate(isoDate : String) : String {
//    val isoDate = "2026-01-22T12:20:14.632Z"

    // 1. Parse the ISO string to an Instant
    val instant = Instant.parse(isoDate)

    // 2. Convert to local date (using system default timezone)
    val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()

    // 3. Format to "1st Feb, 2026"
    // Note: 'd' produces 1, 2, 3... This formatter handles the month name.
    val formatter = DateTimeFormatter.ofPattern("d MMM, yyyy", Locale.ENGLISH)
    val formattedDate = localDate.format(formatter)

    println(formattedDate) // Output: 22 Jan, 2026
    return formattedDate
}

fun formatDateTime(isoDate : String) : String {
//    val isoDate = "2026-01-22T12:20:14.632Z"

    // 1. Parse the ISO string to an Instant
    val instant = Instant.parse(isoDate)

    // 2. Convert to local date (using system default timezone)
    val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()

    // 3. Format to "1st Feb, 2026"
    // Note: 'd' produces 1, 2, 3... This formatter handles the month name.
    val formatter = DateTimeFormatter.ofPattern("d MMM, yyyy HH:mm", Locale.ENGLISH)
    val formattedDate = localDate.format(formatter)



    println(formattedDate) // Output: 22 Jan, 2026
    return formattedDate
}
fun getTimeMillisN(isoDate: String): Long {
    return try {
        Instant.parse(isoDate).toEpochMilli()
    } catch (_: Exception) {
        val localDate = LocalDate.parse(isoDate)
        localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}

fun getTimeMillis(isoDate : String): Long{
    val instant = Instant.parse(isoDate)
    val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()

    // 3. Format to "1st Feb, 2026"
    // Note: 'd' produces 1, 2, 3... This formatter handles the month name.
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    val formattedDate = localDate.format(formatter)

    val formatter1 = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return formatter1.parse(formattedDate)?.time?:0L
}

fun getDateMillis(isoDate : String): Long{
    val instant = Instant.parse(isoDate)
    val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()

    // 3. Format to "1st Feb, 2026"
    // Note: 'd' produces 1, 2, 3... This formatter handles the month name.
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    val formattedDate = localDate.format(formatter)

    val formatter1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter1.parse(formattedDate)?.time?:0L
}
fun formatDateTime(millis: Long?): String {
    if (millis == null) return ""
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return sdf.format(Date(millis))
}

fun formatDate(millis: Long?): String {
    if (millis == null) return ""
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(millis))
}

fun pickDateTime(
    context: Context,
    initialMillis: Long?,
    onPicked: (Long) -> Unit
) {
    val cal = Calendar.getInstance()
    if (initialMillis != null) cal.timeInMillis = initialMillis

    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH)
    val day = cal.get(Calendar.DAY_OF_MONTH)
    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val minute = cal.get(Calendar.MINUTE)

    DatePickerDialog(context, { _, y, m, d ->
        val cal2 = Calendar.getInstance().apply {
            set(Calendar.YEAR, y)
            set(Calendar.MONTH, m)
            set(Calendar.DAY_OF_MONTH, d)
        }

        TimePickerDialog(context, { _, hh, mm ->
            cal2.set(Calendar.HOUR_OF_DAY, hh)
            cal2.set(Calendar.MINUTE, mm)
            cal2.set(Calendar.SECOND, 0)
            cal2.set(Calendar.MILLISECOND, 0)
            onPicked(cal2.timeInMillis)
        }, hour, minute, true).show()

    }, year, month, day).show()
}

fun pickDate(
    context: Context,
    initialMillis: Long?,
    onPicked: (Long) -> Unit
) {
    val cal = Calendar.getInstance()
    if (initialMillis != null) cal.timeInMillis = initialMillis

    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH)
    val day = cal.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(context, { _, y, m, d ->
        val cal2 = Calendar.getInstance().apply {
            set(Calendar.YEAR, y)
            set(Calendar.MONTH, m)
            set(Calendar.DAY_OF_MONTH, d)

        }
        onPicked(cal2.timeInMillis)

    }, year, month, day).show()
}

fun millisToIso(millis: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(millis))
}

fun millisToIsoDate(millis: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(millis))
}

fun toApiMap(values: Map<String, FieldValue>): Map<String, Any?> =
    values.mapValues { (_, v) ->
        when (v) {
            is FieldValue.Text -> v.value.toString()
            is FieldValue.Pick -> v.value.toString()
            is FieldValue.DateTime -> v.millis?.let { millisToIso(it)}
            is FieldValue.Date -> v.millis?.let { millisToIsoDate(it)}
            is FieldValue.Select -> v.value
        }
    }

fun openDialer(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    // Check if there's an app that can handle this intent before starting
//    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
//    }
}

//fun toApiMapMillis(values: Map<String, FieldValue>): Map<String, Any?> =
//    values.mapValues { (_, v) ->
//        when (v) {
//            is FieldValue.Text -> v.value
//            is FieldValue.Pick -> v.value
//            is FieldValue.DateTime -> v.millis
//        }
//    }