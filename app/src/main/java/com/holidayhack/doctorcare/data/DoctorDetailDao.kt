package com.holidayhack.doctorcare.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.holidayhack.doctorcare.modals.Doctor

@Dao
interface DoctorDetailDao {

    @Insert
    suspend fun insertDoc (doctor : Doctor)

    @Query ("SELECT * FROM doctor")
    fun getDoctor () : Doctor

}