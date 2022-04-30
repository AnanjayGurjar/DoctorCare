package com.holidayhack.doctorcare.authenticate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.holidayhack.doctorcare.ui.activity.MainActivity
import com.holidayhack.doctorcare.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private val TAG = "SignUpActivity"
    lateinit var binding: ActivitySignUpBinding

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.etEmail.addTextChangedListener{
            binding.etEmail.error = null
        }
        binding.etPassword.addTextChangedListener{
            binding.etPassword.error = null
        }

        auth = FirebaseAuth.getInstance()
        binding.signupBtn.setOnClickListener{

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (validate(email, password, confirmPassword)){

                auth.createUserWithEmailAndPassword(email , password)
                    .addOnCompleteListener(this){
                        val intent = Intent (this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "onCreate: ${it.message}", )
                    }
            }
        }
    }

    private fun validate(email: String, password: String, confirmPassword : String) : Boolean {
        var valid = true

        if (email.isBlank()){
            binding.etEmail.error = "Please enter the email"
            valid = false
        }
        if (password.isBlank()){
            binding.etPassword.error = "Please enter the password"
            valid = false
        }
        if(password.length < 6){
            binding.etPassword.error = "Password length should be minimum of 6 characters"
            valid = false
        }
        if (confirmPassword.isBlank()){
            binding.etPassword.error = "Please enter the password again to confirm"
            valid = false
        }
        if (confirmPassword != password){
            binding.etPassword.error = "Password does not matched with the above password"
            valid = false
        }
        return valid
    }
}