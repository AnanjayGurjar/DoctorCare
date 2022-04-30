package com.holidayhack.doctorcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.holidayhack.doctorcare.authenticate.LoginActivity
import com.holidayhack.doctorcare.authenticate.SignUpActivity
import com.holidayhack.doctorcare.databinding.ActivityLoginBinding
import com.holidayhack.doctorcare.databinding.ActivityMainBinding
import com.holidayhack.doctorcare.ui.fragments.PatientDetailFragment

class MainActivity : AppCompatActivity() {

    val user = FirebaseAuth.getInstance().currentUser

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(user == null){
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

}