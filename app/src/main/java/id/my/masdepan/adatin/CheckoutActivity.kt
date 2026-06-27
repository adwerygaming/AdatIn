package id.my.masdepan.adatin

import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checkout)

        val pakaianId = intent.getIntExtra("productId", -1)
        val selectedProductSize = intent.getStringExtra("selectedProductSize")

        val pakaian = daftarPakaian.find { it.id == pakaianId }

        if (pakaian == null) {
            Toast.makeText(this, "Pakaian Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        val etProductName = findViewById<TextView>(R.id.etProductName)
        val etProductSizeSelected = findViewById<TextView>(R.id.etProductSizeSelected)

        etProductName.text = pakaian.nama
        etProductSizeSelected.text = "Ukuran ${selectedProductSize}"

        val etNamaPenyewa = findViewById<TextInputEditText>(R.id.etRenterName)
        val etNomorWA = findViewById<TextInputEditText>(R.id.etRenterPhoneNumber)
        val etTanggal = findViewById<TextInputEditText>(R.id.etTanggal)
        val etJam = findViewById<TextInputEditText>(R.id.etJam)
        val rbDelivery = findViewById<RadioButton>(R.id.rbDelivery)

        val isDelivery = rbDelivery.isChecked

        etTanggal.setOnClickListener {
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Pilih Rentang Waktu Sewa")
                .build()

            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                val startDateMillis = selection.first
                val endDateMillis = selection.second

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedStart = sdf.format(Date(startDateMillis))
                val formattedEnd = sdf.format(Date(endDateMillis))

                etTanggal.setText("$formattedStart - $formattedEnd")
            }

            dateRangePicker.show(supportFragmentManager, "DATE_RANGE_PICKER")
        }

        etJam.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Pilih Waktu Ambil")
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", timePicker.hour, timePicker.minute)
                etJam.setText(formattedTime)
            }

            timePicker.show(supportFragmentManager, "TIME_PICKER")
        }


    }
}