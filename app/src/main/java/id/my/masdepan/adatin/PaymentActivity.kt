package id.my.masdepan.adatin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)

        val productId = intent.getIntExtra("productId", -1)
        val selectedProductSize = intent.getStringExtra("selectedProductSize") ?: "None"
        val renterName = intent.getStringExtra("RenterName")
        val renterPhoneNumber = intent.getStringExtra("RenterPhoneNumber")
        val isDelivery = intent.getBooleanExtra("isDelivery", false)
        val renterAddress = intent.getStringExtra("renterAddress")
        val rentingDays = intent.getIntExtra("rentingDays", -1)
        val totalPrice = intent.getIntExtra("totalPrice", -1)
        val startRentDateMs = intent.getLongExtra("startRentDateMs", -1)
        val endRentDateMs = intent.getLongExtra("endRentDateMs", -1)
        val quantity = intent.getIntExtra("quantity", 1)

        val pakaian = daftarPakaian.find { it.id == productId }
        if (pakaian == null) {
            Toast.makeText(this, "Pakaian Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        val tvPaymentProductName = findViewById<TextView>(R.id.tvPaymentProductName)
        val tvPaymentProductSelectedSize = findViewById<TextView>(R.id.tvPaymentProductSelectedSize)
        val tvPaymentProductPrice = findViewById<TextView>(R.id.tvPaymentProductPrice)
        val tvPaymentProductImage = findViewById<ImageView>(R.id.tvPaymentProductImage)

        val tvPaymentRenterName = findViewById<TextView>(R.id.tvPaymentRenterName)
        val tvPaymentRenterPhoneNumber = findViewById<TextView>(R.id.tvPaymentRenterPhoneNumber)
        val tvPaymentDeliveryMethod = findViewById<TextView>(R.id.tvPaymentDeliveryMethod)
        val tvPaymentRenterAddress = findViewById<TextView>(R.id.tvPaymentRenterAddress)
        val tvPaymentRenterAddressLayout = findViewById<LinearLayout>(R.id.tvPaymentRenterAddressLayout)
        val tvPaymentRentingDuration = findViewById<TextView>(R.id.tvPaymentRentingDuration)

        val tvPaymentDetailsProductNameLabel = findViewById<TextView>(R.id.tvPaymentDetailsProductNameLabel)
        val tvPaymentDetailsProductName = findViewById<TextView>(R.id.tvPaymentDetailsProductName)
        val tvPaymentDetailsDeliveryFeeLayout = findViewById<LinearLayout>(R.id.tvPaymentDetailsDeliveryFeeLayout)
        val tvPaymentDetailsDeliveryFee = findViewById<TextView>(R.id.tvPaymentDetailsDeliveryFee)
        val tvPaymentTotal = findViewById<TextView>(R.id.tvPaymentTotal)

        val PaymentCheckoutBtn = findViewById<Button>(R.id.PaymentCheckoutBtn)

        // product info
        tvPaymentProductName.text = pakaian.nama
        tvPaymentProductSelectedSize.text = "Ukuran ${selectedProductSize}"
        tvPaymentProductPrice.text = "Rp${pakaian.harga_per_hari.toRupiahFormat()} / hari"

        tvPaymentProductImage.load("${pakaian.gambar}.jpg") {
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

        // renter info
        tvPaymentRenterName.text = renterName
        tvPaymentRenterPhoneNumber.text = renterPhoneNumber
        tvPaymentRenterAddress.text = renterAddress

        if (isDelivery) {
            tvPaymentRenterAddressLayout.visibility = View.VISIBLE
            tvPaymentDeliveryMethod.text = "Delivery Ke Rumah"
        } else {
            tvPaymentRenterAddressLayout.visibility = View.GONE
            tvPaymentDeliveryMethod.text = "Ambil Di Tempat"
        }

        tvPaymentRentingDuration.text = "${rentingDays} hari"

        // payment details
        var subtotal = totalPrice
        val deliveryFee = 15000

        tvPaymentDetailsProductNameLabel.text = pakaian.nama
        tvPaymentDetailsProductName.text = "${quantity}x Rp${pakaian.harga_per_hari.toRupiahFormat()}"
        if (isDelivery) {
            tvPaymentDetailsDeliveryFeeLayout.visibility = View.VISIBLE
            tvPaymentDetailsDeliveryFee.text = "Rp${deliveryFee.toRupiahFormat()}"
            subtotal += deliveryFee
        } else {
            tvPaymentDetailsDeliveryFeeLayout.visibility = View.GONE
        }

        tvPaymentTotal.text = "Rp${subtotal.toRupiahFormat()}"

        PaymentCheckoutBtn.setOnClickListener {
            val invoiceId = "INV-${System.currentTimeMillis()}"

            val newOrder = TransactionItem(
                invoiceId,
                pakaian.id,
                startRentDateMs,
                endRentDateMs,
                when (isDelivery) { true -> TipePengambilan.DELIVERY; false -> TipePengambilan.PICKUP },
                selectedProductSize,
                subtotal,
                StatusSewa.SEDANG_DIPROSES
            )

            GlobalVariable.activeAccount?.addPurchaseHistory(newOrder)

            val dialog = MaterialAlertDialogBuilder(this)
                .setView(R.layout.dialog_loading)
                .setCancelable(false)
                .show()

            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()

                val intent = Intent(this, PaymentSuccessActivity::class.java)
                intent.putExtra("invoiceId", invoiceId)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }, 2000)
        }
    }
}