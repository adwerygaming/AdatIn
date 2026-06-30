package id.my.masdepan.adatin

import java.text.NumberFormat
import java.util.Locale

// This attaches the function to EVERY Integer in your app
fun Int.toRupiahFormat(): String {
    // Force the locale to Indonesian so it uses a dot (.) instead of a comma (,)
    val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
    return formatter.format(this)
}