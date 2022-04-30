package com.holidayhack.doctorcare.data

import android.app.Application
import com.holidayhack.doctorcare.modals.Doctor

class DoctorRepository (context : Application) {
    private var doctorDao : DoctorDetailDao = DoctorRoomDatabase.getDataBase(context).doctorDetailDao()

    suspend fun insertDoctor (doctor : Doctor){
        return doctorDao.insertDoc(doctor)
    }

    fun getDoctor () : Doctor{
        return doctorDao.getDoctor()
    }

}