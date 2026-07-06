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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import id.my.masdepan.adatin.adapter.PakaianAdapter

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

        val cgDaerahChipGroup = findViewById<ChipGroup>(R.id.cgDaerahChipGroup)
        val filters = daftarPakaian.map { it.daerah }.distinct().sorted().toList()

        var allPakaian = daftarPakaian.toList()

        for (value in filters) {
            val chip = layoutInflater.inflate(R.layout.item_filter_chip, cgDaerahChipGroup, false) as Chip

            chip.apply {
                text = value
                isCheckable = true
            }

            cgDaerahChipGroup.addView(chip)
        }

        cgDaerahChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val selectedChipId = checkedIds.first()
                val selectedChip = group.findViewById<Chip>(selectedChipId)
                val filterValue = selectedChip.text.toString()

                val filteredList = allPakaian.filter { it.daerah == filterValue }
                allPakaian = filteredList;
                adapter.updateData(filteredList)
            } else {
                allPakaian = daftarPakaian.toList()
                adapter.updateData(daftarPakaian)
            }
        }

        val etSearch = findViewById<EditText>(R.id.etSearch)

        etSearch.doAfterTextChanged { editable ->
            val query = editable.toString().trim().lowercase()

            if (query.isEmpty()) {
                adapter.updateData(allPakaian)
            } else {
                val filteredList = allPakaian.filter { pakaian ->
                    pakaian.nama.lowercase().contains(query)
                }

                adapter.updateData(filteredList)
            }
        }

    }
}