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

        val pakaian = daftarPakaian.find { it.id == pakaianId }
        if (pakaian == null) {
            Toast.makeText(this, "Pakaian Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        val ivDetailImage = findViewById<ImageView>(R.id.ivProductImage)
        val tvDetailNama = findViewById<TextView>(R.id.tvProductName)
        val tvDetailDeskripsi = findViewById<TextView>(R.id.tvProductDescription)
        val tvKetersediaan = findViewById<TextView>(R.id.tvAvailibility)
        val btnSewaSekarang = findViewById<Button>(R.id.btnSewaSekarang)
        val cgUkuran = findViewById<ChipGroup>(R.id.cgProductSizeSelection)

        ivDetailImage.load("${pakaian.gambar}.jpg") {
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

        tvDetailNama.text = pakaian.nama
        tvDetailDeskripsi.text = pakaian.deskripsi
        tvKetersediaan.text = if (pakaian.tersedia) "Tersedia" else "Habis"

        if (!pakaian.tersedia) {
            btnSewaSekarang.isEnabled = false
        }

        btnSewaSekarang.setOnClickListener {
            val checkedChipId = cgUkuran.checkedChipId
            val selectedChip = findViewById<Chip>(checkedChipId)
            val selectedSize = selectedChip.text.toString()

            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("productId", pakaianId)
            intent.putExtra("selectedProductSize", selectedSize)
            this.startActivity(intent)
        }
    }
}