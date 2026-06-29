package id.my.masdepan.adatin

import android.os.Bundle
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
            
            val activeTransactions = GlobalVariable.semuaTransaksi.filter { it.status == StatusSewa.SEDANG_DIPROSES }

            println(GlobalVariable.semuaTransaksi)
            println(activeTransactions)
            if (activeTransactions.isEmpty()) {
                println("NO ACTIVE TRANSACTIONS.")
                return@launch
            }

            println("${activeTransactions.size} ACTIVE TRANSACTIONS.")

            for (trx in activeTransactions) {
                delay(3000)

                println(trx)
                println(trx.status)
                println(trx.tipe_pengambilan)

                if (trx.tipe_pengambilan == TipePengambilan.DELIVERY) {
                    trx.status = StatusSewa.SEDANG_DIANTAR
                } else {
                    trx.status = StatusSewa.SIAP_DIAMBIL
                }

                adapter.notifyDataSetChanged()
            }
        }
    }
}