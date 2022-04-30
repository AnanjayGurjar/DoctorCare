package com.holidayhack.doctorcare.modals

data class Doctor(
    val name: String,
    val profilePhoto: String,
    val email: String,
    val degrees: List<String>,
    val experience: Int
) {

}