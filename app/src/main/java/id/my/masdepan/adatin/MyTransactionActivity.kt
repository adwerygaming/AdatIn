package id.my.masdepan.adatin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.my.masdepan.adatin.adapter.MyTransactionAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyTransactionActivity : AppCompatActivity() {
    private lateinit var adapter: MyTransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_transaction)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val topAppBar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            val intent = Intent(this, MyProfileActivity::class.java)
            startActivity(intent)
        }

        val rvTransactions = findViewById<RecyclerView>(R.id.rvTransactions)
        rvTransactions.layoutManager = LinearLayoutManager(this)

        val allTransactions = GlobalVariable.activeAccount?.getMyPurchaseHistory()  ?: emptyList()

        println("ALL TRANSACTIONS onCreate")
        println(allTransactions)

        adapter = MyTransactionAdapter(allTransactions)
        rvTransactions.adapter = adapter

        updateView()

        lifecycleScope.launch {
            adapter.notifyDataSetChanged()

            for (trx in allTransactions) {
                val transactionId = trx.id

                if (trx.status == StatusSewa.SEDANG_DIPROSES) {
                    delay(3000)

                    if (trx.tipe_pengambilan == TipePengambilan.DELIVERY) {
                        GlobalFunction.changePenyewaanStatus(transactionId, StatusSewa.SEDANG_DIANTAR)
                        adapter.notifyDataSetChanged()

                        delay(6000)
                        GlobalFunction.changePenyewaanStatus(transactionId, StatusSewa.SAMPAI_TUJUAN)
                    } else {
                        GlobalFunction.changePenyewaanStatus(transactionId, StatusSewa.SIAP_DIAMBIL)
                    }
                }

                if (trx.status == StatusSewa.MENUNGGU_KONFIRMASI_PEMBATALAN) {
                    delay(6000)

                    Toast.makeText(this@MyTransactionActivity, "Penjual telah mengkonfirmasi pembatalan. Dana telah dikembalikan ke metode pembayaran anda.", Toast.LENGTH_LONG).show()
                    GlobalFunction.changePenyewaanStatus(transactionId, StatusSewa.DIBATALKAN)
                }

                adapter.notifyDataSetChanged()

            }
        }
    }

    override fun onResume() {
        super.onResume()

        val allTransactions = GlobalVariable.activeAccount?.getMyPurchaseHistory() ?: emptyList()
        adapter.updateData(allTransactions)

        updateView()
    }

    fun updateView() {
        val MyTransactionScrollViewGroup = findViewById<LinearLayout>(R.id.MyTransactionScrollViewGroup)
        val MyTransactionEmptyDetailGroup = findViewById<LinearLayout>(R.id.MyTransactionEmptyDetailGroup)

        val allTransactions = GlobalVariable.activeAccount?.getMyPurchaseHistory() ?: emptyList()

        if (allTransactions.isEmpty()) {
            MyTransactionScrollViewGroup.visibility = View.GONE
            MyTransactionEmptyDetailGroup.visibility = View.VISIBLE
        } else {
            MyTransactionScrollViewGroup.visibility = View.VISIBLE
            MyTransactionEmptyDetailGroup.visibility = View.GONE
        }
    }
}