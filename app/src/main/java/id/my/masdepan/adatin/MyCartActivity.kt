package id.my.masdepan.adatin

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.my.masdepan.adatin.adapter.MyCartAdapter
import id.my.masdepan.adatin.model.GlobalVariable

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

        val allMyCarts = GlobalVariable.activeAccount?.getMyCart() ?: emptyList()

        updateView()

        adapter = MyCartAdapter(allMyCarts)
        rvCarts.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        val allMyCarts = GlobalVariable.activeAccount?.getMyCart() ?: emptyList()
        adapter.updateData(allMyCarts)

        updateView()
    }

    fun updateView() {
        val MyCartScrollViewGroup = findViewById<LinearLayout>(R.id.MyCartScrollViewGroup)
        val MyCartEmptyDetailGroup = findViewById<LinearLayout>(R.id.MyCartEmptyDetailGroup)

        val allMyCarts = GlobalVariable.activeAccount?.getMyCart() ?: emptyList()

        if (allMyCarts.isEmpty()) {
            MyCartScrollViewGroup.visibility = View.GONE
            MyCartEmptyDetailGroup.visibility = View.VISIBLE
        } else {
            MyCartScrollViewGroup.visibility = View.VISIBLE
            MyCartEmptyDetailGroup.visibility = View.GONE
        }
    }
}