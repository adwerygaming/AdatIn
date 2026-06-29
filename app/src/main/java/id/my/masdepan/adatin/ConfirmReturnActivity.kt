package id.my.masdepan.adatin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import okio.Okio

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

        val tvConfirmReturnTitle = findViewById<TextView>(R.id.tvConfirmReturnTitle);
        val btnConfirmReturn = findViewById<Button>(R.id.btnConfirmReturn)
        val btnCancelReturn = findViewById<Button>(R.id.btnCancelReturn)

        tvConfirmReturnTitle.text = "Kembalikan ${produk?.nama}?"

        btnConfirmReturn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(R.layout.dialog_loading)
                .setCancelable(false)
                .show()

            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()

                val intent = Intent(this, MyTransactionActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                updateStatus(transactionId, StatusSewa.SELESAI)

                Toast.makeText(this, "Pengembalian Berhasil. Terima kasih.", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }, 2000)
        }

        btnCancelReturn.setOnClickListener {
            finish()
        }
    }
}