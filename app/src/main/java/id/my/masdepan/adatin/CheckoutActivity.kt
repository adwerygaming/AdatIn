package id.my.masdepan.adatin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
        val quantity = intent.getIntExtra("quantity", 1)

        val pakaian = daftarPakaian.find { it.id == pakaianId }

        if (pakaian == null) {
            Toast.makeText(this, "Pakaian Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        val etProductName = findViewById<TextView>(R.id.etProductName)
        val etProductSizeSelected = findViewById<TextView>(R.id.etProductSizeSelected)

        val etRenterName = findViewById<TextInputEditText>(R.id.etRenterName)
        val etRenterPhoneNumber = findViewById<TextInputEditText>(R.id.etRenterPhoneNumber)

        val etRentingDate = findViewById<TextInputEditText>(R.id.etRentingDate)
        val etPickupTime = findViewById<TextInputEditText>(R.id.etPickupTime)
        val etAddressLayout = findViewById<TextInputLayout>(R.id.etAddressLayout)
        val etAddress = findViewById<TextInputEditText>(R.id.etAddress)

        val rgDeliveryGroup = findViewById<RadioGroup>(R.id.rgDeliveryMethod)
        val rbDelivery = findViewById<RadioButton>(R.id.rbDelivery)

        val tvProductPrice = findViewById<TextView>(R.id.tvProductPrice)
        val tvTotalPrice = findViewById<TextView>(R.id.tvTotalPrice)
        val tvRentingDuration = findViewById<TextView>(R.id.tvRentingDuration)
        val tvProductQuantity = findViewById<TextView>(R.id.tvProductQuantity)

        val checkoutBtn = findViewById<Button>(R.id.checkoutBtn)

        var startRentDateMs = 0L
        var endRentDateMs = 0L
        var totalPrice = 0
        var rentingDays = 0

        etProductName.text = pakaian.nama
        etProductSizeSelected.text = "Ukuran ${selectedProductSize}"
        tvProductPrice.text = "Rp${pakaian.harga_per_hari.toRupiahFormat()} / hari"
        tvProductQuantity.text = "${quantity} Pcs"

        tvRentingDuration.text = "-"
        tvTotalPrice.text = "-"

        var isDelivery = rbDelivery.isChecked

        rgDeliveryGroup.setOnCheckedChangeListener { _, i ->
            isDelivery = (i == rbDelivery.id)
            if (i == rbDelivery.id) {
                etAddressLayout.visibility = View.VISIBLE
            } else {
                etAddressLayout.visibility = View.GONE
            }
        }

        etRentingDate.setOnClickListener {
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Pilih Rentang Waktu Sewa")
                .build()

            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                val startDateMillis = selection.first
                val endDateMillis = selection.second

                startRentDateMs = startDateMillis
                endRentDateMs = endDateMillis

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedStart = sdf.format(Date(startDateMillis))
                val formattedEnd = sdf.format(Date(endDateMillis))

                etRentingDate.setText("$formattedStart - $formattedEnd")

                val diffMillis = endDateMillis - startDateMillis
                val tempRentingDays = (diffMillis / (1000L * 60 * 60 * 24))
                rentingDays = tempRentingDays.toInt() + 1
                tvRentingDuration.text = "${rentingDays} hari"

                totalPrice = (rentingDays) * (pakaian.harga_per_hari * quantity)
                tvTotalPrice.text = "Rp${totalPrice.toRupiahFormat()}"
            }

            dateRangePicker.show(supportFragmentManager, "DATE_RANGE_PICKER")
        }

        etPickupTime.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Pilih Waktu Ambil")
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", timePicker.hour, timePicker.minute)
                etPickupTime.setText(formattedTime)
            }

            timePicker.show(supportFragmentManager, "TIME_PICKER")
        }

        checkoutBtn.setOnClickListener {
            if (etRenterName.text.toString().isEmpty()) {
                etRenterName.error = "Nama Penyewa Tidak Boleh Kosong"
                return@setOnClickListener
            }

            if (etRenterPhoneNumber.text.toString().isEmpty()) {
                etRenterPhoneNumber.error = "Nomor WA Tidak Boleh Kosong"
                return@setOnClickListener
            }

            if (isDelivery) {
                if (etAddress.text.toString().isEmpty()) {
                    etAddress.error = "Alamat Tidak Boleh Kosong"
                    return@setOnClickListener
                }
            }

            if (etRentingDate.text.toString().isEmpty()) {
                etRentingDate.error = "Tanggal Sewa Tidak Boleh Kosong"
                return@setOnClickListener
            }

            if (etPickupTime.text.toString().isEmpty()) {
                etPickupTime.error = "Waktu Pengambilan Tidak Boleh Kosong"
                return@setOnClickListener
            }

            etRenterName.error = null
            etRenterPhoneNumber.error = null
            etAddress.error = null
            etRentingDate.error = null
            etPickupTime.error = null

            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("productId", pakaianId)
            intent.putExtra("quantity", quantity)
            intent.putExtra("selectedProductSize", selectedProductSize)
            intent.putExtra("RenterName", etRenterName.text.toString())
            intent.putExtra("RenterPhoneNumber", etRenterPhoneNumber.text.toString())
            intent.putExtra("isDelivery", isDelivery)
            intent.putExtra("renterAddress", etAddress.text.toString())
            intent.putExtra("rentingDays", rentingDays)
            intent.putExtra("totalPrice", totalPrice)
            intent.putExtra("startRentDateMs", startRentDateMs)
            intent.putExtra("endRentDateMs", endRentDateMs)
            this.startActivity(intent)

        }
    }
}