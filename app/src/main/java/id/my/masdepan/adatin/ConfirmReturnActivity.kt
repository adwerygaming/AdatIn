package id.my.masdepan.adatin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ConfirmReturnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirm_return)

        val transactionId = intent.getStringExtra("transactionId")

        if (transactionId == null) {
            Toast.makeText(this, "Tidak ditemukan transactionId. Coba lagi nanti!", Toast.LENGTH_LONG).show()
            return
        }

        val transaction = GlobalVariable.activeAccount?.getPurchaseById(transactionId)

        if (transaction == null) {
            Toast.makeText(this, "Transaksi Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        val produk = daftarPakaian.find { it.id == transaction.pakaianId }

        if (produk == null) {
            Toast.makeText(this, "Pakaian Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        val tvConfirmReturnProductName = findViewById<TextView>(R.id.tvConfirmReturnProductName)
        val tvConfirmReturnProductSize = findViewById<TextView>(R.id.tvConfirmReturnProductSize)
        val ivConfirmReturnProductImage = findViewById<ImageView>(R.id.ivConfirmReturnProductImage)
        val btnTransactionProductReturnAction = findViewById<Button>(R.id.btnTransactionProductReturnAction)
        val btnTransactionProductReturnBack = findViewById<Button>(R.id.btnTransactionProductReturnBack)

        tvConfirmReturnProductName.text = produk.nama
        tvConfirmReturnProductSize.text = "Ukuran ${transaction.ukuran}"

        ivConfirmReturnProductImage.load("${produk.gambar}.jpg") {
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

        btnTransactionProductReturnAction.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(R.layout.dialog_loading)
                .setCancelable(false)
                .show()

            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()

                val intent = Intent(this, MyTransactionActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                GlobalFunction.changePenyewaanStatus(transactionId, StatusSewa.SELESAI)

                Toast.makeText(this, "Pengembalian Berhasil. Terima kasih.", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }, 2000)
        }

        btnTransactionProductReturnBack.setOnClickListener {
            finish()
        }
    }
}