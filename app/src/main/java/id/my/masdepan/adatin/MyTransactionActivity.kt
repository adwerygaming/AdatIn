package id.my.masdepan.adatin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_transaction)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_cart
        bottomNav.setupBottomNav(this)

        val rvTransactions = findViewById<RecyclerView>(R.id.rvTransactions)
        rvTransactions.layoutManager = LinearLayoutManager(this)

        val adapter = PenyewaanAdapter(GlobalVariable.semuaTransaksi)
        rvTransactions.adapter = adapter

        lifecycleScope.launch {
            adapter.notifyDataSetChanged()

            val allTransactions = GlobalVariable.semuaTransaksi

            for (trx in allTransactions) {
                val transactionId = trx.id

                if (trx.status == StatusSewa.SEDANG_DIPROSES) {
                    delay(3000)

                    if (trx.tipe_pengambilan == TipePengambilan.DELIVERY) {
                        updateStatus(transactionId, StatusSewa.SEDANG_DIANTAR)
                        adapter.notifyDataSetChanged()

                        delay(6000)
                        updateStatus(transactionId, StatusSewa.SAMPAI_TUJUAN)
                    } else {
                        updateStatus(transactionId, StatusSewa.SIAP_DIAMBIL)
                    }
                }

                if (trx.status == StatusSewa.MENUNGGU_KONFIRMASI_PEMBATALAN) {
                    delay(6000)

                    Toast.makeText(this@MyTransactionActivity, "Penjual telah mengkonfirmasi pembatalan. Dana telah dikembalikan ke metode pembayaran anda.", Toast.LENGTH_LONG).show()
                    updateStatus(transactionId, StatusSewa.DIBATALKAN)
                }

                adapter.notifyDataSetChanged()

            }
        }
    }
}