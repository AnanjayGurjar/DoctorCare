package com.holidayhack.doctorcare.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.holidayhack.doctorcare.R
import com.holidayhack.doctorcare.databinding.FragmentPatientDetailBinding
import com.holidayhack.doctorcare.databinding.FragmentSearchPatientBinding
import com.holidayhack.doctorcare.modals.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PatientDetailFragment : Fragment() {

    private val TAG = "PatientDetailFragment"
    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    private lateinit var binding: FragmentPatientDetailBinding
    var patient : Patient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPatientDetailBinding.inflate(inflater,container,false)
        return binding.getRoot()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE

        val id = PatientDetailFragmentArgs.fromBundle(requireArguments()).id
        getPatientById(id)

        Log.d(TAG, "onViewCreated: $patient")

    }


    private fun getPatientById(patientId: Long){
        db.collection(currentUser!!.uid).document(patientId.toString())
            .get()
            .addOnSuccessListener { result ->
                if(result != null){
                    patient= result.toObject(Patient::class.java)
                    binding.progressBar.visibility = View.GONE
                    Log.d(TAG, "Success: $patient" )
                    setPatientData()
                }
            }
            .addOnFailureListener { exception ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Error occured $exception", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }



    private fun setPatientData(){
        if (patient != null){
            binding.appBar.title = patient!!.name
            binding.tvPatientAgeNum.text = patient!!.age.toString()
            binding.tvPatientWeightNum.text = patient!!.weigth.toString()
            binding.tvPatientId.text = patient!!.patientId.toString()
            binding.tvConsultancyFee.text = "Rs. ${patient!!.consultationFee}"
            binding.patientIssueText.text = patient!!.issue
        }else{
            Toast.makeText(requireContext(), "Patient doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }

}