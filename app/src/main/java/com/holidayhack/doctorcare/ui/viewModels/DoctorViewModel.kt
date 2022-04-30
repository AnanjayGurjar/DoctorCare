package com.holidayhack.doctorcare.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.holidayhack.doctorcare.data.DoctorRepository
import com.holidayhack.doctorcare.modals.Doctor
import kotlinx.coroutines.launch

class DoctorViewModel (application: Application) : AndroidViewModel(application) {

    val repo : DoctorRepository = DoctorRepository(application)

    fun saveDoctor (doctor: Doctor){
        viewModelScope.launch {
            repo.insertDoctor(doctor)
        }
    }

    fun getDoctor () : Doctor?{
        return repo.getDoctor()
    }

}