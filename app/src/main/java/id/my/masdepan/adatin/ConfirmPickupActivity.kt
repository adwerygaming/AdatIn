package id.my.masdepan.adatin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.my.masdepan.adatin.model.GlobalVariable
import id.my.masdepan.adatin.model.StatusSewa

class ConfirmPickupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirm_pickup)

        val activeAccount = GlobalVariable.activeAccount
        if (activeAccount == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }

        val transactionId = intent.getStringExtra("transactionId")

        if (transactionId == null) {
            Toast.makeText(this, "Tidak ditemukan transactionId. Coba lagi nanti!", Toast.LENGTH_LONG).show()
            return
        }

        val transaction = activeAccount.getTransactionById(transactionId)

        if (transaction == null) {
            Toast.makeText(this, "Transaksi Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        val btnConfirmPickupAction = findViewById<Button>(R.id.btnConfirmPickupAction)

        btnConfirmPickupAction.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(R.layout.dialog_loading)
                .setCancelable(false)
                .show()

            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()

                transaction.updateRentingStatus(StatusSewa.SEDANG_DISEWA)

                val intent = Intent(this, MyTransactionActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                Toast.makeText(this, "Pengambilan dikonfirmasi. Masa sewa dimulai dari sekarang.", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }, 2000)
        }
    }
}