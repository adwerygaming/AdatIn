package id.my.masdepan.adatin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.my.masdepan.adatin.model.UserAccount

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Delay 2 detik sebelum pindah ke Login atau Main
        Handler(Looper.getMainLooper()).postDelayed({
            checkSession()
        }, 2000)
    }

    private fun checkSession() {
        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val savedEmail = sharedPref.getString("email", null)
        val savedPassword = sharedPref.getString("password", null)

        if (savedEmail != null && savedPassword != null) {
            // Auto login
            val account = UserAccount(savedEmail, savedPassword)
            val login = account.login()

            if (login) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return
            } else {
                Toast.makeText(this, "Sesi anda telah berakhir, silahkan login ulang.", Toast.LENGTH_LONG).show()
            }
        }

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }
}
