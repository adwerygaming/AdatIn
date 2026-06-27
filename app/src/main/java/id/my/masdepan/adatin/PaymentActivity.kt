package id.my.masdepan.adatin

import android.os.Bundle
import android.view.View
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

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)

        val productId = intent.getIntExtra("productId", -1)
        val selectedProductSize = intent.getStringExtra("selectedProductSize")
        val renterName = intent.getStringExtra("RenterName")
        val renterPhoneNumber = intent.getStringExtra("RenterPhoneNumber")
        val isDelivery = intent.getBooleanExtra("isDelivery", false)
        val renterAddress = intent.getStringExtra("renterAddress")
        val rentingDays = intent.getIntExtra("rentingDays", -1)
        val totalPrice = intent.getIntExtra("totalPrice", -1)

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

        val rgPayment = findViewById<RadioGroup>(R.id.rgPayment)
        val rbBCA = findViewById<RadioButton>(R.id.rbBCA)
        val rbMandiri = findViewById<RadioButton>(R.id.rbMandiri)
        val rbGoPay = findViewById<RadioButton>(R.id.rbGoPay)
        val rbDana = findViewById<RadioButton>(R.id.rbDana)
        val rbQRIS = findViewById<RadioButton>(R.id.rbQRIS)

        tvPaymentProductName.text = pakaian.nama
        tvPaymentProductSelectedSize.text = "Ukuran ${selectedProductSize}"
        tvPaymentProductPrice.text = "Rp${pakaian.harga_per_hari} / hari"

        tvPaymentProductImage.load("${pakaian.gambar}.jpg") {
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

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

    }
}