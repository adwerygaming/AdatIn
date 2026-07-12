package id.my.masdepan.adatin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import id.my.masdepan.adatin.model.GlobalVariable

class MyProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_account
        bottomNav.setupBottomNav(this)

        val account = GlobalVariable.activeAccount

        if (account == null) {
            return
        }

        val menuTransaksi = findViewById<LinearLayout>(R.id.menuTransaksi)
        val menuAbout = findViewById<LinearLayout>(R.id.menuAbout)
        val menuTeam = findViewById<LinearLayout>(R.id.menuTeam)
        val menuEditProfile = findViewById<LinearLayout>(R.id.menuEditProfile)
        val btnLogout = findViewById<Button>(R.id.btnLogout)


        updateProfile()

        menuTransaksi.setOnClickListener {
            val intent = Intent(this, MyTransactionActivity::class.java)
            startActivity(intent)
        }

        menuEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        menuTeam.setOnClickListener {
            val intent = Intent(this, OurTeamActivity::class.java)
            startActivity(intent)
        }

        menuAbout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Tentang Applikasi")
                .setMessage("AdatIn adalah aplikasi Android untuk menyewa pakaian adat dari berbagai daerah di Indonesia. Selain memudahkan proses penyewaan, aplikasi ini juga menyediakan informasi sejarah dan filosofi pakaian adat guna mendukung pelestarian budaya Indonesia.")
                .setPositiveButton("OK", null)
                .show()
        }

        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar dari akun?")
                .setPositiveButton("Ya") { _, _ ->
                    val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("email", null)
                    editor.putString("password", null)
                    editor.apply()

                    GlobalVariable.activeAccount = null

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        updateProfile()
    }

    fun updateProfile() {
        val account = GlobalVariable.activeAccount
        if (account == null) {
            return
        }

        val tvName = findViewById<TextView>(R.id.tvName)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val ivProfilePicture = findViewById<ShapeableImageView>(R.id.ivProfilePicture)

        val fullname = account.getName()
        val email = account.getEmail()
        val profilePhoto = account.getProfilePhoto()

        ivProfilePicture.load(profilePhoto)
        tvName.text = fullname
        tvEmail.text = email
    }
}