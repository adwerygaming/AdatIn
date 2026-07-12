package id.my.masdepan.adatin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import id.my.masdepan.adatin.adapter.TeamAdapter
import id.my.masdepan.adatin.model.Team

class OurTeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_our_team)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvTeam)
        val toolbar = findViewById<MaterialToolbar>(R.id.outTeamAppBar)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val teamList = listOf(
            Team("Yoga Aditiya Putra","(25.12.3645)",R.drawable.team_pic_yoga),
            Team("Iqbal Dimas Saputra","(25.12.3633)",R.drawable.team_pic_iqbal),
            Team("Indyra Zulaeyka Rabbani","(25.12.3625)",R.drawable.team_pic_indyra),
            Team("Fikki Rahmat Maulana","(25.12.3618)",R.drawable.team_pic_fikki),
            Team("Dhevan Adhitya Prasetyo","(25.12.3654)",R.drawable.team_pic_devan),
        )

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = TeamAdapter(teamList)
    }
}
