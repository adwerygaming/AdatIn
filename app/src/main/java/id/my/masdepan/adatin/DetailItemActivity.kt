package id.my.masdepan.adatin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class DetailItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_item)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        val pakaianId = intent.getIntExtra("productId", -1)
        val productSelectedSize = intent.getStringExtra("productSelectedSize")
        val quantity = intent.getIntExtra("quantity", 1)

        val pakaian = daftarPakaian.find { it.id == pakaianId }
        if (pakaian == null) {
            Toast.makeText(this, "Pakaian Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        val ivDetailProductImage = findViewById<ImageView>(R.id.ivDetailProductImage)
        val tvDetailProductName = findViewById<TextView>(R.id.tvDetailProductName)
        val tvDetailProductDescription = findViewById<TextView>(R.id.tvDetailProductDescription)
        val tvDetailProductRating = findViewById<TextView>(R.id.tvDetailProductRating)
        val tvDetailProductPrice = findViewById<TextView>(R.id.tvDetailProductPrice)
        val tvDetailProductAvailability = findViewById<TextView>(R.id.tvDetailProductAvailability)
        val tvDetailProductQuantityValue = findViewById<TextView>(R.id.tvDetailProductQuantityValue)

        val cgDetailProductSizeSelection = findViewById<ChipGroup>(R.id.cgDetailProductSizeSelection)

        val btnDetailProductRentNow = findViewById<Button>(R.id.btnDetailProductRentNow)
        val btnDetailProductAddToCart = findViewById<Button>(R.id.btnDetailProductAddToCart)
        val btnDetailProductDecrease = findViewById<Button>(R.id.btnDetailProductDecrease)
        val btnDetailProductIncrease = findViewById<Button>(R.id.btnDetailProductIncrease)

        ivDetailProductImage.load("${pakaian.gambar}.jpg") {
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

        tvDetailProductName.text = pakaian.nama
        tvDetailProductDescription.text = pakaian.deskripsi
        tvDetailProductRating.text = pakaian.rating.toString()
        tvDetailProductPrice.text = "Rp${pakaian.harga_per_hari.toRupiahFormat()} / hari"
        tvDetailProductAvailability.text = if (pakaian.tersedia) "Tersedia" else "Habis"

        if (!pakaian.tersedia) {
            btnDetailProductRentNow.isEnabled = false
            btnDetailProductAddToCart.isEnabled = false
        }

        if (productSelectedSize != null) {
            val size_s = findViewById<Chip>(R.id.chipS)
            val size_l = findViewById<Chip>(R.id.chipL)
            val size_m = findViewById<Chip>(R.id.chipM)
            val size_xl = findViewById<Chip>(R.id.chipXL)

            println(productSelectedSize)

            when (productSelectedSize) {
                "S" -> size_s.isChecked = true
                "M" -> size_m.isChecked = true
                "L" -> size_l.isChecked = true
                "XL" -> size_xl.isChecked = true
            }
        }

        var productQuantity = quantity

        tvDetailProductQuantityValue.text = productQuantity.toString()

        btnDetailProductIncrease.setOnClickListener {
            productQuantity++
            tvDetailProductQuantityValue.text = productQuantity.toString()
        }

        btnDetailProductDecrease.setOnClickListener {
            if (productQuantity > 1) {
                productQuantity--
                tvDetailProductQuantityValue.text = productQuantity.toString()
            }
        }

        btnDetailProductAddToCart.setOnClickListener {
            val checkedChipId = cgDetailProductSizeSelection.checkedChipId
            val selectedChip = findViewById<Chip>(checkedChipId)
            val selectedSize = selectedChip.text.toString()

            val cartItem = CartItem(pakaianId, productQuantity, UkuranPakaian.valueOf(selectedSize))
            GlobalVariable.activeAccount?.addToCart(cartItem)

            Toast.makeText(this, "${pakaian.nama} berhasil ditambahkan ke keranjang", Toast.LENGTH_LONG).show()
        }

        btnDetailProductRentNow.setOnClickListener {
            val checkedChipId = cgDetailProductSizeSelection.checkedChipId
            val selectedChip = findViewById<Chip>(checkedChipId)
            val selectedSize = selectedChip.text.toString()

            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("productId", pakaianId)
            intent.putExtra("selectedProductSize", selectedSize)
            intent.putExtra("quantity", productQuantity)
            this.startActivity(intent)
        }
    }
}