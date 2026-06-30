package id.my.masdepan.adatin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class DetailItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_item)
        val pakaianId = intent.getIntExtra("productId", -1)
        val productSelectedSize = intent.getStringExtra("productSelectedSize")
        val quantity = intent.getIntExtra("quantity", 1)

        val pakaian = daftarPakaian.find { it.id == pakaianId }
        if (pakaian == null) {
            Toast.makeText(this, "Pakaian Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        val ivDetailImage = findViewById<ImageView>(R.id.ivProductImage)
        val tvDetailNama = findViewById<TextView>(R.id.tvProductName)
        val tvDetailDeskripsi = findViewById<TextView>(R.id.tvProductDescription)
        val tvKetersediaan = findViewById<TextView>(R.id.tvAvailibility)
        val btnRentNow = findViewById<Button>(R.id.btnRentNow)
        val cgUkuran = findViewById<ChipGroup>(R.id.cgProductSizeSelection)
        val btnAddToCart = findViewById<Button>(R.id.btnAddToCart)
        val tvQuantity = findViewById<TextView>(R.id.tvDetailProductQuantity)
        val btnDecreaseQuantity = findViewById<Button>(R.id.btnDecreaseQuantity)
        val btnIncreaseQuantity = findViewById<Button>(R.id.btnIncreaseQuantity)

        ivDetailImage.load("${pakaian.gambar}.jpg") {
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

        tvDetailNama.text = pakaian.nama
        tvDetailDeskripsi.text = pakaian.deskripsi
        tvKetersediaan.text = if (pakaian.tersedia) "Tersedia" else "Habis"

        if (!pakaian.tersedia) {
            btnRentNow.isEnabled = false
            btnAddToCart.isEnabled = false
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

        tvQuantity.text = productQuantity.toString()

        btnIncreaseQuantity.setOnClickListener {
            productQuantity++
            tvQuantity.text = productQuantity.toString()
        }

        btnDecreaseQuantity.setOnClickListener {
            if (productQuantity > 1) {
                productQuantity--
                tvQuantity.text = productQuantity.toString()
            }
        }

        btnAddToCart.setOnClickListener {
            val checkedChipId = cgUkuran.checkedChipId
            val selectedChip = findViewById<Chip>(checkedChipId)
            val selectedSize = selectedChip.text.toString()

            val cartItem = CartItem(pakaianId, productQuantity, UkuranPakaian.valueOf(selectedSize))
            GlobalVariable.activeAccount?.addToCart(cartItem)

            Toast.makeText(this, "${pakaian.nama} berhasil ditambahkan ke keranjang", Toast.LENGTH_LONG).show()
        }

        btnRentNow.setOnClickListener {
            val checkedChipId = cgUkuran.checkedChipId
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