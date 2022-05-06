package com.holidayhack.doctorcare.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.navigation.fragment.findNavController
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



        binding.searchIdBtn.setOnClickListener {
            var id  = binding.searchPatientId.text
            if(id.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Please enter the id", Toast.LENGTH_SHORT).show()
            }else if(!id.isDigitsOnly()){
                Toast.makeText(requireContext(), "Invalid id, please check", Toast.LENGTH_SHORT).show()
            }else{
                findNavController().navigate(
                    SearchPatientFragmentDirections.actionSearchPatientFragmentToPatientDetailFragment(id.toString().toLong())
                )
            }

        }

        binding.searchNameBtn.setOnClickListener {
            var name = binding.searchPatientName.text
            findNavController().navigate(
                SearchPatientFragmentDirections.actionSearchPatientFragmentToPatientListFragment()
            )
        }

        binding.viewAllPatientBtn.setOnClickListener {
            findNavController().navigate(
                SearchPatientFragmentDirections.actionSearchPatientFragmentToPatientListFragment()
            )
        }
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