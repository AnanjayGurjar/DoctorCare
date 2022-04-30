package com.holidayhack.doctorcare.modals

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "doctor")
data class Doctor(
    val name: String,
    val profilePhoto: Bitmap, //have used typeConvertor to convert from bitmap to byteArray and vise verse
    val email: String,
    val degrees: List<String>,
    val experience: Int
){
    @PrimaryKey (autoGenerate = true)
    val id : Long = 0
}