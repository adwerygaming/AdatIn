package id.my.masdepan.adatin

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.widget.doAfterTextChanged

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_home
        bottomNav.setupBottomNav(this)

        val rvKatalog = findViewById<RecyclerView>(R.id.rvKatalog)
        rvKatalog.layoutManager = LinearLayoutManager(this)

        val adapter = PakaianAdapter(daftarPakaian)
        rvKatalog.adapter = adapter

        val etSearch = findViewById<EditText>(R.id.etSearch)

        etSearch.doAfterTextChanged { editable ->
            val query = editable.toString().trim().lowercase()

            if (query.isEmpty()) {
                adapter.updateData(daftarPakaian)
            } else {
                val filteredList = daftarPakaian.filter { pakaian ->
                    pakaian.nama.lowercase().contains(query)
                }

                adapter.updateData(filteredList)
            }
        }

    }
}