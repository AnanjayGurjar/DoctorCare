package com.holidayhack.doctorcare.modals


data class Patient(
    val name: String,
    val age: Int,
    val weigth: String,
    val issue: String,
    val patientId: Int,
    val consultationFee: String
)