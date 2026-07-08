package id.my.masdepan.adatin

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import id.my.masdepan.adatin.adapter.PakaianAdapter

class SearchProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        println("DEBUG SearchProductAcitivity")

        val ivArrowBack = findViewById<ImageView>(R.id.ivArrowBack)
        val rvKatalog = findViewById<RecyclerView>(R.id.rvKatalog)

        ivArrowBack.setOnClickListener {
            finish()
        }

        val adapter = PakaianAdapter(daftarPakaian)
        rvKatalog.layoutManager = LinearLayoutManager(this)
        rvKatalog.adapter = adapter

        var allPakaian = daftarPakaian.toList()

        val cgDaerahChipGroup = findViewById<ChipGroup>(R.id.cgDaerahChipGroup)
        val filters = daftarPakaian.map { it.daerah }.distinct().sorted().toList()

        val allChip = layoutInflater.inflate(R.layout.item_filter_chip, cgDaerahChipGroup, false) as Chip
        val allChipVariant = allChip.apply {
            text = "Semua"
            isCheckable = true
            id = View.generateViewId()
        }
        cgDaerahChipGroup.addView(allChipVariant)
        cgDaerahChipGroup.check(allChipVariant.id)

        for (value in filters) {
            val chip = layoutInflater.inflate(R.layout.item_filter_chip, cgDaerahChipGroup, false) as Chip

            chip.apply {
                text = value
                isCheckable = true
                id = View.generateViewId()
            }

            cgDaerahChipGroup.addView(chip)
        }

        cgDaerahChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            println("DEBUG $checkedIds")

                val selectedChipId = checkedIds.first()
                val selectedChip = group.findViewById<Chip>(selectedChipId)
                val filterValue = selectedChip.text.toString()

                println("DECODED VALUE $filterValue")

                var filteredList = daftarPakaian.filter { it.daerah == filterValue }

                if (filterValue == "Semua") {
                    filteredList = daftarPakaian.toList()
                    adapter.updateData(daftarPakaian)
                }

                allPakaian = filteredList
                adapter.updateData(filteredList)
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

        println(allPakaian)
        adapter.updateData(allPakaian)
    }
}