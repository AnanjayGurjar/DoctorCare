package com.holidayhack.doctorcare.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.holidayhack.doctorcare.databinding.FragmentSearchPatientBinding
import com.holidayhack.doctorcare.modals.Patient

class SearchPatientFragment : Fragment() {

    private val TAG = "SearchPatientFragment"
    private lateinit var binding: FragmentSearchPatientBinding
    val db = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser
    private var allPatients = ArrayList<Patient>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchPatientBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun getAllPatients(){
        db.collection(currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val patient: Patient = document.toObject(Patient::class.java)
                    allPatients.add(patient)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    private fun getPatientById(patientId: Int): Patient?{
        var patient: Patient? = null
        db.collection(currentUser!!.uid).document(patientId.toString())
            .get()
            .addOnSuccessListener { result ->
                if(result != null){
                    patient= result.toObject(Patient::class.java)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        return patient
    }

    private fun getPatientByName(pateintName: String): ArrayList<Patient>{
        var patients = ArrayList<Patient>()
        db.collection(currentUser!!.uid)
            .whereEqualTo("name", pateintName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val patient: Patient = document.toObject(Patient::class.java)
                    patients.add(patient);
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        return patients
    }



}