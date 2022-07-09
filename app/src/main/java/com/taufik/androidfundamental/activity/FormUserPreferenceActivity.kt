package com.taufik.androidfundamental.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.data.UserModel
import com.taufik.androidfundamental.databinding.ActivityFormUserPreferenceBinding
import com.taufik.androidfundamental.utils.UserPreference
import kotlin.properties.Delegates

class FormUserPreferenceActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormUserPreferenceBinding.inflate(layoutInflater)
    }

    private lateinit var userModel: UserModel
    private var formType by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getParcelable()
        formType()
        setAction()
    }

    private fun getParcelable() {
        userModel = intent.getParcelableExtra<UserModel>("USER") as UserModel
        formType = intent.getIntExtra(EXTRA_TYPE_FORM, 0)
    }

    private fun formType() {
        var actionBarTitle = ""
        var btnTitle = ""

        when (formType) {
            TYPE_ADD -> {
                actionBarTitle = "Tambah Baru"
                btnTitle = "Simpan"
            }

            TYPE_EDIT -> {
                actionBarTitle = "Ubah"
                btnTitle = "Update"
                showPreferenceInForm()
            }
        }

        supportActionBar?.apply {
            title = actionBarTitle
            setDisplayHomeAsUpEnabled(true)
        }

        binding.btnSave.text = btnTitle
    }

    private fun showPreferenceInForm() = with(binding) {
        etName.setText(userModel.name)
        etEmail.setText(userModel.email)
        etAge.setText(userModel.age.toString())
        etPhoneNumber.setText(userModel.phoneNumber)
        if (userModel.isLove) {
            rbYes.isChecked = true
        } else {
            rbNo.isChecked = true
        }
    }

    private fun setAction() = with(binding) {
        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val age = etAge.text.toString().trim()
            val phoneNumber = etPhoneNumber.text.toString().trim()
            val isLoveMu = rgLoveMu.checkedRadioButtonId == R.id.rbYes

            when {
                name.isEmpty() -> {
                    etName.error = FIELD_REQUIRED
                    return@setOnClickListener
                }

                email.isEmpty() -> {
                    etEmail.error = FIELD_REQUIRED
                    return@setOnClickListener
                }

                !isValidEmail(email) -> {
                    etEmail.error = FIELD_IS_NOT_VALID
                    return@setOnClickListener
                }

                age.isEmpty() -> {
                    etAge.error = FIELD_REQUIRED
                    return@setOnClickListener
                }

                phoneNumber.isEmpty() -> {
                    etPhoneNumber.error = FIELD_REQUIRED
                    return@setOnClickListener
                }

                !TextUtils.isDigitsOnly(phoneNumber) -> {
                    etPhoneNumber.error = FIELD_DIGIT_ONLY
                    return@setOnClickListener
                }

                else -> saveUser(name, email, age, phoneNumber, isLoveMu)
            }

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_RESULT, userModel)
            setResult(RESULT_CODE, resultIntent)

            finish()
        }
    }

    private fun saveUser(name: String, email: String, age: String, phoneNumber: String, loveMu: Boolean) {
        val userPreference = UserPreference(this)
        userModel.apply {
            this.name = name
            this.email = email
            this.age = Integer.parseInt(age)
            this.phoneNumber = phoneNumber
            this.isLove = loveMu
        }

        userPreference.setUser(userModel)
        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_TYPE_FORM = "extra_type_form"
        const val EXTRA_RESULT = "extra_result"
        const val RESULT_CODE = 101
        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2
        private const val FIELD_REQUIRED = "Field tidak boleh kosong"
        private const val FIELD_DIGIT_ONLY = "Hanya boleh terisi numerik"
        private const val FIELD_IS_NOT_VALID = "Email tidak valid"
    }
}