package id.my.masdepan.adatin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ConfirmPickupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirm_pickup)

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

        val btnConfirm = findViewById<Button>(R.id.btnConfirmOrder)

        btnConfirm.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(R.layout.dialog_loading)
                .setCancelable(false)
                .show()

            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()

                val intent = Intent(this, MyTransactionActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                GlobalFunction.changePenyewaanStatus(transactionId, StatusSewa.SEDANG_DISEWA)

                Toast.makeText(this, "Konfirmasi Berhasil. It's all yours.", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }, 2000)
        }
    }
}