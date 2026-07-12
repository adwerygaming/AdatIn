package id.my.masdepan.adatin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.my.masdepan.adatin.model.UserAccount

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val autoFillEmail = intent.getStringExtra("autoFillEmail")

        val etEmailLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.etEmailLayout)
        val etEmail = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etEmail)
        val etPasswordLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.etPasswordLayout)
        val etPassword = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        // for better ux
        if (autoFillEmail != null) {
            etEmail.setText(autoFillEmail)
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty()) {
                etEmailLayout.error = "Email Tidak Boleh Kosong"
                return@setOnClickListener
            } else {
                etEmailLayout.error = null
            }

            if (password.isEmpty()) {
                etPasswordLayout.error = "Password Tidak Boleh Kosong"
                return@setOnClickListener
            } else {
                etPasswordLayout.error = null
            }

            val account = UserAccount(email, password)
            val loginSuccess = account.login()

            if (loginSuccess) {
                val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)

                val editor = sharedPref.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                etEmailLayout.error = "Email atau password salah"
                etPasswordLayout.error = "Email atau password salah"
            }
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
