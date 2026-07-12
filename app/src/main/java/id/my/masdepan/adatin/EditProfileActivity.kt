package id.my.masdepan.adatin

import android.content.Intent
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

        val activeAccount = GlobalVariable.activeAccount
        if (activeAccount == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }

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
        val fullName = activeAccount.getName()
        val email = activeAccount.getEmail()
        val address = activeAccount.getAddress()
        val phoneNumber = activeAccount.getPhoneNumber()

        etFullName.setText(fullName)
        etEmail.setText(email)
        etAddress.setText(address)
        etPhoneNumber.setText(phoneNumber)

        fun simpanProgres(): Boolean {
            etFullNameLayout.error = null
            etEmailLayout.error = null
            etPhoneNumberLayout.error = null
            etAddressLayout.error = null

            val fullNameInput = etFullName.text?.toString()
            val emailInput = etEmail.text?.toString()
            val phoneNumberInput = etPhoneNumber.text?.toString()
            val addressInput = etAddress.text?.toString()

            if (fullNameInput != null) {
                if (fullNameInput.length > 0) {
                    if (fullNameInput.length < 3) {
                        etFullNameLayout.error = "Nama minimal 3 karakter."
                        return false
                    }

                    activeAccount.updateName(fullNameInput)
                }
            }

            if (emailInput != null) {
                if (emailInput.length > 0) {
                    if (emailInput.length < 3) {
                        etEmailLayout.error = "Email minimal 3 karakter."
                        return false
                    }

                    if (!emailInput.contains("@")) {
                        etEmailLayout.error = "Email tidak valid."
                        return false
                    }

                    activeAccount.updateEmail(emailInput)
                }
            }

            if (phoneNumberInput != null) {
                if (phoneNumberInput.length > 0) {
                    if (phoneNumberInput.length < 10) {
                        etPhoneNumberLayout.error = "Nomor minimal 10 karakter."
                        return false
                    }

                    activeAccount.updatePhoneNumber(phoneNumberInput)
                }
            }

            if (addressInput != null) {
                if (addressInput.length > 0) {
                    if (addressInput.length < 10) {
                        etAddressLayout.error = "Alamat minimal 10 karakter."
                        return false
                    }

                    activeAccount.updateAddress(addressInput)
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
