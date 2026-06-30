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
import kotlinx.coroutines.launch

class MyCartActivity : AppCompatActivity() {
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

        println("MY CARTS A")
        println(allMyCarts)

        if (allMyCarts.isNullOrEmpty()) {
            return
        }

        val adapter = CartAdapter(allMyCarts)
        rvCarts.adapter = adapter

        lifecycleScope.launch {
            adapter.notifyDataSetChanged()
            adapter.updateData(allMyCarts)

            println("MY CARTS B")
            println(allMyCarts)
        }
    }
}