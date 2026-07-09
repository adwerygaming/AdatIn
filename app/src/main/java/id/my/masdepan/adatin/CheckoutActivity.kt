package id.my.masdepan.adatin

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import id.my.masdepan.adatin.model.GlobalVariable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checkout)

        val account = GlobalVariable.activeAccount
        if (account == null) {
            Toast.makeText(this, "Akun Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

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

        val etRenterNameLayout = findViewById<TextInputLayout>(R.id.etRenterNameLayout)
        val etRenterName = findViewById<TextInputEditText>(R.id.etRenterName)
        val etRenterPhoneNumberLayout = findViewById<TextInputLayout>(R.id.etRenterPhoneNumberLayout)
        val etRenterPhoneNumber = findViewById<TextInputEditText>(R.id.etRenterPhoneNumber)

        val etRentingDateLayout = findViewById<TextInputLayout>(R.id.etRentingDateLayout)
        val etRentingDate = findViewById<TextInputEditText>(R.id.etRentingDate)
        val etPickupTimeLayout = findViewById<TextInputLayout>(R.id.etPickupTimeLayout)
        val etPickupTime = findViewById<TextInputEditText>(R.id.etPickupTime)
        val etCheckoutAddressLayout = findViewById<TextInputLayout>(R.id.etCheckoutAddressLayout)
        val etCheckoutAddress = findViewById<TextInputEditText>(R.id.etCheckoutAddress)

        val btnCheckoutDelivery = findViewById<MaterialButton>(R.id.btnCheckoutDelivery)
        val btnCheckoutPickup = findViewById<MaterialButton>(R.id.btnCheckoutPickup)

        val tvProductPrice = findViewById<TextView>(R.id.tvProductPrice)
        val tvTotalPrice = findViewById<TextView>(R.id.tvTotalPrice)
        val tvRentingDuration = findViewById<TextView>(R.id.tvRentingDuration)
        val tvProductQuantity = findViewById<TextView>(R.id.tvProductQuantity)

        val checkoutBtn = findViewById<Button>(R.id.checkoutBtn)

        var startRentDateMs = 0L
        var endRentDateMs = 0L
        var totalPrice = 0
        var rentingDays = 0

        etRenterName.setText(account.fullName)
        etRenterPhoneNumber.setText(account.phoneNumber)
        etCheckoutAddress.setText(account.address)

        etProductName.text = pakaian.nama
        etProductSizeSelected.text = "Ukuran ${selectedProductSize}"
        tvProductPrice.text = "Rp${pakaian.harga_per_hari.toRupiahFormat()} / hari"
        tvProductQuantity.text = "${quantity} Pcs"

        tvRentingDuration.text = "-"
        tvTotalPrice.text = "-"

        var isDelivery = false

        fun toggleButton(btn: MaterialButton, state: Boolean) {
            if (state) {
                val white = ContextCompat.getColor(this, android.R.color.white)
                val brown = ContextCompat.getColor(this, R.color.brand_deep_brown)

                btn.backgroundTintList = ColorStateList.valueOf(brown)
                btn.setTextColor(white)
                btn.iconTint = ColorStateList.valueOf(white)
                btn.strokeWidth = 0
            } else {
                val brown = ContextCompat.getColor(this, R.color.brand_deep_brown)

                btn.backgroundTintList = ColorStateList.valueOf(android.graphics.Color.TRANSPARENT)
                btn.setTextColor(brown)
                btn.iconTint = ColorStateList.valueOf(brown)
                btn.strokeColor = ColorStateList.valueOf(brown)
                btn.strokeWidth = 6
            }
        }

        btnCheckoutDelivery.setOnClickListener {
            isDelivery = true
            etCheckoutAddressLayout.visibility = View.VISIBLE

            toggleButton(btnCheckoutDelivery, true)
            toggleButton(btnCheckoutPickup, false)
        }

        btnCheckoutPickup.setOnClickListener {
            isDelivery = false
            etCheckoutAddressLayout.visibility = View.GONE

            toggleButton(btnCheckoutDelivery, false)
            toggleButton(btnCheckoutPickup, true)
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

                val diffMillis = endDateMillis - startDateMillis
                val tempRentingDays = (diffMillis / (1000L * 60 * 60 * 24))
                rentingDays = tempRentingDays.toInt() + 1

                println("rentingDays: $rentingDays")
                println("tempRentingDays: ${tempRentingDays.toInt()}")

                val maxRentingDays = GlobalVariable.maxRentingDays

                if (rentingDays > maxRentingDays) {
                    etRentingDateLayout.error = "Maksimal waktu penyewaan 7 hari."
                    return@addOnPositiveButtonClickListener
                }

                etRentingDateLayout.error = null

                etRentingDate.setText("$formattedStart - $formattedEnd")

                tvRentingDuration.text = "${rentingDays} hari"

                totalPrice = (rentingDays) * (pakaian.harga_per_hari * quantity)
                tvTotalPrice.text = "Rp${totalPrice.toRupiahFormat()}"
            }

            dateRangePicker.show(supportFragmentManager, "DATE_RANGE_PICKER")
        }

        etPickupTime.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(7)
                .setMinute(0)
                .setTitleText("Pilih Waktu Ambil")
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val hours = timePicker.hour
                val minutes = timePicker.minute

                val openTime = GlobalVariable.openTime.split(":")
                val closeTime = GlobalVariable.closeTime.split(":")

                val openHour = openTime[0].toInt()
                val openMinute= openTime[1].toInt()

                val closeHour = closeTime[0].toInt()
                val closeMinute = closeTime[1].toInt()

                if (hours < openHour || (hours == openHour && minutes < openMinute)) {
                    etPickupTimeLayout.error = "Waktu Pengambilan Minimal ${GlobalVariable.openTime}"
                    return@addOnPositiveButtonClickListener
                } else if (hours > closeHour || (hours == closeHour && minutes > closeMinute)) {
                    etPickupTimeLayout.error = "Waktu Pengambilan Maximal ${GlobalVariable.closeTime}"
                    return@addOnPositiveButtonClickListener
                }

                etPickupTimeLayout.error = null

                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)

                etPickupTime.setText(formattedTime)
            }

            timePicker.show(supportFragmentManager, "TIME_PICKER")
        }

        checkoutBtn.setOnClickListener {
            if (etRenterName.text.toString().isEmpty()) {
                etRenterNameLayout.error = "Nama Penyewa Tidak Boleh Kosong"
                return@setOnClickListener
            } else {
                etRenterNameLayout.error = null
            }

            if (etRenterPhoneNumber.text.toString().isEmpty()) {
                etRenterPhoneNumberLayout.error = "Nomor WA Tidak Boleh Kosong"
                return@setOnClickListener
            } else {
                etRenterPhoneNumberLayout.error = null
            }

            if (isDelivery) {
                if (etCheckoutAddress.text.toString().isEmpty()) {
                    etCheckoutAddressLayout.error = "Alamat Tidak Boleh Kosong"
                    return@setOnClickListener
                }

                etCheckoutAddressLayout.error = null
            } else {
                etCheckoutAddressLayout.error = null
            }

            if (etRentingDate.text.toString().isEmpty()) {
                etRentingDateLayout.error = "Tanggal Sewa Tidak Boleh Kosong"
                return@setOnClickListener
            } else {
                etRentingDateLayout.error = null
            }

            if (etPickupTime.text.toString().isEmpty()) {
                etPickupTimeLayout.error = "Waktu Pengambilan Tidak Boleh Kosong"
                return@setOnClickListener
            } else {
                etPickupTimeLayout.error = null
            }

            if (etPickupTime.text.toString().isEmpty()) {
                etPickupTimeLayout.error = "Waktu Pengambilan Tidak Boleh Kosong"
                return@setOnClickListener
            } else {
                etPickupTimeLayout.error = null
            }

            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("productId", pakaianId)
            intent.putExtra("quantity", quantity)
            intent.putExtra("selectedProductSize", selectedProductSize)
            intent.putExtra("RenterName", etRenterName.text.toString())
            intent.putExtra("RenterPhoneNumber", etRenterPhoneNumber.text.toString())
            intent.putExtra("isDelivery", isDelivery)
            intent.putExtra("renterAddress", etCheckoutAddress.text.toString())
            intent.putExtra("rentingDays", rentingDays)
            intent.putExtra("totalPrice", totalPrice)
            intent.putExtra("startRentDateMs", startRentDateMs)
            intent.putExtra("endRentDateMs", endRentDateMs)
            this.startActivity(intent)

        }
    }
}