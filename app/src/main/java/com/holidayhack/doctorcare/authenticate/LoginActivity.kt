package com.holidayhack.doctorcare.authenticate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.holidayhack.doctorcare.MainActivity
import com.holidayhack.doctorcare.R
import com.holidayhack.doctorcare.databinding.ActivityLoginBinding
import com.holidayhack.doctorcare.databinding.ActivitySignUpBinding


//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
class LoginActivity : AppCompatActivity() {
    private val TAG = "SignUpActivity"
    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth = FirebaseAuth.getInstance()
        binding.signupBtn.setOnClickListener{
            binding.etEmail.error = null
            binding.etPassword.error = null

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validate(email, password)){

                auth.signInWithEmailAndPassword(email , password)
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

    private fun validate(email: String, password: String): Boolean {
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
        return valid
    }
}