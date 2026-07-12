package id.my.masdepan.adatin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.my.masdepan.adatin.model.Customer

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etFullNameLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.etFullNameLayout)
        val etFullName = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etFullName)
        val etEmailLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.etEmailLayout)
        val etEmail = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etEmail)
        val etPasswordLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.etPasswordLayout)
        val etPassword = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etPassword)
        val etConfirmPasswordLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.etConfirmPasswordLayout)
        val etConfirmPassword = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnRegister.setOnClickListener {
            val fullName = etFullName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (fullName.isEmpty()) {
                etFullNameLayout.error = "Nama Lengkap Tidak Boleh Kosong"
                return@setOnClickListener
            } else if (fullName.length < 3) {
                etFullNameLayout.error = "Nama minimal 3 karakter."
                return@setOnClickListener
            } else if (!fullName.matches(Regex("^[a-zA-Z\\s']+$"))) {
                etFullNameLayout.error = "Nama Lengkap hanya boleh berisi huruf"
                return@setOnClickListener
            } else {
                etFullNameLayout.error = null
            }

            if (email.isEmpty()) {
                etEmailLayout.error = "Email Tidak Boleh Kosong"
                return@setOnClickListener
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmailLayout.error = "Email tidak valid."
                return@setOnClickListener
            } else {
                etEmailLayout.error = null
            }

            if (password.isEmpty()) {
                etPasswordLayout.error = "Password Tidak Boleh Kosong"
                return@setOnClickListener
            } else if (password.length < 6) {
                etPasswordLayout.error = "Password minimal 6 karakter."
                return@setOnClickListener
            } else {
                etPasswordLayout.error = null
            }

            if (confirmPassword.isEmpty()) {
                etConfirmPasswordLayout.error = "Konfirmasi Password Tidak Boleh Kosong"
                return@setOnClickListener
            } else {
                etConfirmPasswordLayout.error = null
            }

            if (password != confirmPassword) {
                etConfirmPasswordLayout.error = "Password tidak cocok"
                return@setOnClickListener
            } else {
                etConfirmPasswordLayout.error = null
            }

            val account = Customer(email, password, fullName, null, null, R.drawable.user_placeholder)
            val registerResult = account.register()

            if (registerResult) {
                Toast.makeText(this, "Registrasi Berhasil, silakan login", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                etEmailLayout.error = "Email sudah terdaftar"
            }
        }

        tvLogin.setOnClickListener {
            finish()
        }
    }
}
