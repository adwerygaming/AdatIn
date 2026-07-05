package id.my.masdepan.adatin

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val toolbar = findViewById<MaterialToolbar>(R.id.editAppBar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val etFullName = findViewById<TextInputEditText>(R.id.etFullName)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)

        fun simpanProgres() {
            var account = GlobalVariable.activeAccount

            if (account == null) {
                Toast.makeText(this, "Akun tidak ada. Silahkan login ulang.", Toast.LENGTH_LONG).show()
                return
            }

            val fullName = etFullName.text?.toString()
            val email = etEmail.text?.toString()

            if (fullName != null) {
                if (fullName.length < 3) {
                    Toast.makeText(this, "Nama minimal 3 karakter.", Toast.LENGTH_LONG).show()
                    return
                }

                account.updateName(fullName);
            }

            if (email != null) {
                if (email.length < 3) {
                    Toast.makeText(this, "Email minimal 3 karakter.", Toast.LENGTH_LONG).show()
                    return
                }

                account.updateEmail(email);
            }
        }

        btnSimpan.setOnClickListener {
            simpanProgres()
            finish()
        }
    }
}
