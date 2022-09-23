package com.holidayhack.doctorcare.modals


data class Patient(
    val name: String? = null,
    val age: Int = 0,
    val weigth: String? = null,
    val issue: String? = null,
    val patientId: Long? = null,
    val consultationFee: String? = null
)