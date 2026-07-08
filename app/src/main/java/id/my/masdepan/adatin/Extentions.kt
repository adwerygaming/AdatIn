package id.my.masdepan.adatin

import java.text.NumberFormat
import java.util.Locale

fun Int.toRupiahFormat(): String {
    val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
    return formatter.format(this)
}