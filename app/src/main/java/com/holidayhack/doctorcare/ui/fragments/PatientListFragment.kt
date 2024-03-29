package com.holidayhack.doctorcare.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.holidayhack.doctorcare.R
import com.holidayhack.doctorcare.adapters.PatientAdapter
import com.holidayhack.doctorcare.databinding.FragmentPatientListBinding
import com.holidayhack.doctorcare.databinding.FragmentSearchPatientBinding
import com.holidayhack.doctorcare.modals.Patient

class PatientListFragment : Fragment() {

    private val TAG = "PatientListFragment"
    val db = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser
    private var patientsList = ArrayList<Patient>()

    private lateinit var binding: FragmentPatientListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPatientListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name : String = PatientListFragmentArgs.fromBundle(requireArguments()).name

        if (name == "all"){
            getAllPatients()
        }else{
            getPatientByName(name)
        }
    }

    private fun getAllPatients(){
        db.collection(currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val patient: Patient = document.toObject(Patient::class.java)
                    patientsList.add(patient)
                }
                setUpRecyclerView()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    private fun getPatientByName(pateintName: String){
        db.collection(currentUser!!.uid)
            .whereEqualTo("name", pateintName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val patient: Patient = document.toObject(Patient::class.java)
                    patientsList.add(patient)
                }
                setUpRecyclerView()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }

    private fun setUpRecyclerView(){
        with(binding.patientListRv){
            layoutManager = LinearLayoutManager(requireContext())
            Log.d(TAG, "setUpRecyclerView: ${patientsList}")
            adapter = PatientAdapter(patientsList){
                findNavController().navigate(
                    PatientListFragmentDirections.actionPatientListFragmentToPatientDetailFragment(it)
                )
            }
        }
    }


}