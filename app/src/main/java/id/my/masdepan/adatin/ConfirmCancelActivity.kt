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
import id.my.masdepan.adatin.model.GlobalFunction
import id.my.masdepan.adatin.model.GlobalVariable
import id.my.masdepan.adatin.model.StatusSewa

class ConfirmCancelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirm_cancel)

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

        val btnTransactionProductCancelAction = findViewById<Button>(R.id.btnTransactionProductCancelAction)
        val btnTransactionProductCancelBack = findViewById<Button>(R.id.btnTransactionProductCancelBack)

        btnTransactionProductCancelAction.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setView(R.layout.dialog_loading)
                .setCancelable(false)
                .show()

            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()

                val intent = Intent(this, MyTransactionActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                GlobalFunction.changePenyewaanStatus(transactionId, StatusSewa.MENUNGGU_KONFIRMASI_PEMBATALAN)

                Toast.makeText(this, "Permintaan pembatalan telah dikirim.", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }, 2000)
        }

        btnTransactionProductCancelBack.setOnClickListener {
            finish()
        }
    }
}