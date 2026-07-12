package id.my.masdepan.adatin

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.my.masdepan.adatin.model.GlobalVariable

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val toolbar = findViewById<MaterialToolbar>(R.id.editAppBar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val etFullNameLayout = findViewById<TextInputLayout>(R.id.etFullNameLayout)
        val etFullName = findViewById<TextInputEditText>(R.id.etFullName)
        val etEmailLayout = findViewById<TextInputLayout>(R.id.etEmailLayout)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etAddressLayout = findViewById<TextInputLayout>(R.id.etAddressLayout)
        val etAddress = findViewById<TextInputEditText>(R.id.etAddress)
        val etPhoneNumberLayout = findViewById<TextInputLayout>(R.id.etPhoneNumberLayout)
        val etPhoneNumber = findViewById<TextInputEditText>(R.id.etPhoneNumber)

        // autofill
        val account = GlobalVariable.activeAccount
        if (account != null) {
            val fullName = account.getName()
            val email = account.getEmail()
            val address = account.getAddress()
            val phoneNumber = account.getPhoneNumber()

            etFullName.setText(fullName)
            etEmail.setText(email)
            etAddress.setText(address)
            etPhoneNumber.setText(phoneNumber)
        }

        fun simpanProgres(): Boolean {
            if (account == null) {
                Toast.makeText(this, "Akun tidak ada. Silahkan login ulang.", Toast.LENGTH_LONG).show()
                return false
            }

            etFullNameLayout.error = null
            etEmailLayout.error = null
            etPhoneNumberLayout.error = null
            etAddressLayout.error = null

            val fullName = etFullName.text?.toString()
            val email = etEmail.text?.toString()
            val phoneNumber = etPhoneNumber.text?.toString()
            val address = etAddress.text?.toString()

            if (fullName != null) {
                if (fullName.length > 0) {
                    if (fullName.length < 3) {
                        etFullNameLayout.error = "Nama minimal 3 karakter."
                        return false
                    }

                    account.updateName(fullName)
                }
            }

            if (email != null) {
                if (email.length > 0) {
                    if (email.length < 3) {
                        etEmailLayout.error = "Email minimal 3 karakter."
                        return false
                    }

                    if (!email.contains("@")) {
                        etEmailLayout.error = "Email tidak valid."
                        return false
                    }

                    account.updateEmail(email)
                }
            }

            if (phoneNumber != null) {
                if (phoneNumber.length > 0) {
                    if (phoneNumber.length < 10) {
                        etPhoneNumberLayout.error = "Nomor minimal 10 karakter."
                        return false
                    }

                    account.updatePhoneNumber(phoneNumber)
                }
            }

            if (address != null) {
                if (address.length > 0) {
                    if (address.length < 10) {
                        etAddressLayout.error = "Alamat minimal 10 karakter."
                        return false
                    }

                    account.updateAddress(address)
                }
            }
            return true
        }

        btnSimpan.setOnClickListener {
            if (simpanProgres()) {
                finish()
            }
        }
    }
}
