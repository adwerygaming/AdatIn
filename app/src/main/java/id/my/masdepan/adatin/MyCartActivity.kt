package id.my.masdepan.adatin

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MyCartActivity : AppCompatActivity() {
    private lateinit var adapter: MyCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_cart)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_cart
        bottomNav.setupBottomNav(this)

        val rvCarts = findViewById<RecyclerView>(R.id.rvCarts)
        rvCarts.layoutManager = LinearLayoutManager(this)

        val allMyCarts = GlobalVariable.activeAccount?.getMyCart()

        if (allMyCarts.isNullOrEmpty()) {
            Toast.makeText(this, "You have nothing.", Toast.LENGTH_LONG).show()
            return
        }

        adapter = MyCartAdapter(allMyCarts)
        rvCarts.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        val allMyCarts = GlobalVariable.activeAccount?.getMyCart() ?: emptyList()

        adapter.updateData(allMyCarts)
    }
}