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


        auth = FirebaseAuth.getInstance()
        binding.signupBtn.setOnClickListener{
            binding.userEmailContainerSignup.error = null
            binding.userPasswordContainerSignup.error = null

            val email = binding.userEmail.text.toString()
            val password = binding.userPasswrodSignup.text.toString()

            if (validate(email, password)){
                binding.progressBarSignup.visibility = View.VISIBLE

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

    private fun validate(email: String, password: String): Boolean {
        var valid = true

        if (email.isBlank()){
            binding.userEmailContainerSignup.error = "Please enter the email"
            valid = false
        }
        if (password.isBlank()){
            binding.userPasswordContainerSignup.error = "Please enter the password"
            valid = false
        }
        if(password.length < 6){
            binding.userPasswordContainerSignup.error = "Password length should be minimum of 6 characters"
            valid = false
        }
        return valid
    }
}