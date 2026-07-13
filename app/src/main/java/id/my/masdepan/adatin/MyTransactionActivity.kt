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
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.my.masdepan.adatin.adapter.MyTransactionAdapter
import id.my.masdepan.adatin.model.GlobalVariable
import id.my.masdepan.adatin.model.StatusSewa
import id.my.masdepan.adatin.model.TipePengambilan
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyTransactionActivity : AppCompatActivity() {
    private lateinit var adapter: MyTransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_transaction)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }

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

        val activeAccount = GlobalVariable.activeAccount

        if (activeAccount == null) {
            Toast.makeText(this, "Akun Tidak Ditemukan", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }

        val allTransactions = activeAccount.getMyTransactionHistory()

        adapter = MyTransactionAdapter(allTransactions)
        rvTransactions.adapter = adapter

        updateView()

        lifecycleScope.launch {
            adapter.notifyDataSetChanged()

            for (trx in allTransactions) {
                val transactionId = trx.id
                val transaction = activeAccount.getTransactionById(transactionId)

                if (transaction == null) {
                    continue
                }

                val pakaian = transaction.pakaian
                val status = transaction.getStatus()
                val tipePengambilan = transaction.getPickupMethod()

                if (status == StatusSewa.SEDANG_DIPROSES) {
                    delay(6000)

                    if (tipePengambilan == TipePengambilan.DELIVERY) {
                        transaction.updateRentingStatus(StatusSewa.SEDANG_DIANTAR)
                        this@MyTransactionActivity.showNotification("Update Transaksi #${trx.id}", "${pakaian.nama} sedang dalam perjalanan ke tempatmu")
                        adapter.notifyDataSetChanged()

                        delay(6000)
                        transaction.updateRentingStatus(StatusSewa.SAMPAI_TUJUAN)
                        this@MyTransactionActivity.showNotification("Update Transaksi #${trx.id}", "${pakaian.nama} sudah sampai ke rumahmu!")
                    } else {
                        transaction.updateRentingStatus(StatusSewa.SIAP_DIAMBIL)
                        this@MyTransactionActivity.showNotification("Update Transaksi #${trx.id}", "${pakaian.nama} sudah siap diambil")
                    }
                }

                if (status == StatusSewa.MENUNGGU_KONFIRMASI_PEMBATALAN) {
                    delay(6000)

                    transaction.updateRentingStatus(StatusSewa.DIBATALKAN)
                    this@MyTransactionActivity.showNotification("Transaksi #${trx.id} Dibatalkan", "Penjual telah mengkonfirmasi pembatalan. Dana telah dikembalikan ke metode pembayaran anda.")
                }

                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val activeAccount = GlobalVariable.activeAccount
        if (activeAccount == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }

        val allTransactions = activeAccount.getMyTransactionHistory()
        adapter.updateData(allTransactions)

        updateView()
    }

    fun updateView() {
        val MyTransactionScrollViewGroup = findViewById<LinearLayout>(R.id.MyTransactionScrollViewGroup)
        val MyTransactionEmptyDetailGroup = findViewById<LinearLayout>(R.id.MyTransactionEmptyDetailGroup)

        val activeAccount = GlobalVariable.activeAccount
        val allTransactions = activeAccount?.getMyTransactionHistory() ?: emptyList()

        if (allTransactions.isEmpty()) {
            MyTransactionScrollViewGroup.visibility = View.GONE
            MyTransactionEmptyDetailGroup.visibility = View.VISIBLE
        } else {
            MyTransactionScrollViewGroup.visibility = View.VISIBLE
            MyTransactionEmptyDetailGroup.visibility = View.GONE
        }
    }
}