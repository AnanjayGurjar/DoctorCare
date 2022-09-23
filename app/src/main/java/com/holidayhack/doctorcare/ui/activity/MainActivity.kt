package com.holidayhack.doctorcare.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import com.google.firebase.auth.FirebaseAuth
import com.holidayhack.doctorcare.R
import com.holidayhack.doctorcare.authenticate.SignUpActivity
import com.holidayhack.doctorcare.databinding.ActivityMainBinding
import com.holidayhack.doctorcare.ui.fragments.DoctorProfileFragment
import com.holidayhack.doctorcare.ui.fragments.ViewDoctorProfileFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val user = FirebaseAuth.getInstance().currentUser

//    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//        super.onCreateView(name, context, attrs)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        return binding.root
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        val view = binding.root
        setContentView(R.layout.activity_main)

        if(user == null){
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
//        addFragmentToActivity(supportFragmentManager, ViewDoctorProfileFragment(), binding.container.id)
    }

    fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment?, frameId: Int) {
        val transaction: FragmentTransaction = manager.beginTransaction()
        if (fragment != null) {
            transaction.add(frameId, fragment)
        }
        transaction.commit()
    }

}