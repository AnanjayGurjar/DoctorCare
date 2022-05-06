package com.holidayhack.doctorcare.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.holidayhack.doctorcare.R
import com.holidayhack.doctorcare.databinding.FragmentCreatePatientBinding
import com.holidayhack.doctorcare.modals.Patient


class CreatePatientFragment : Fragment() {
    private val TAG = "CreatePatientFragment"
    val db = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser
    lateinit var binding: FragmentCreatePatientBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatePatientBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val patient = Patient("ananjay", 20, "60kg", "fever", 100, "500")
        binding.savePatientProfileBtn.setOnClickListener {
            addPatient(patient)
        }
    }

    private fun addPatient(patient: Patient){
        db.collection("${currentUser!!.uid}").document(patient.patientId.toString())
            .set(patient)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "New patient id created", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.e(TAG, "addPatient: ${it.message}", )
            }
    }

}