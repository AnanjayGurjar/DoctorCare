package com.holidayhack.doctorcare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.holidayhack.doctorcare.R
import com.holidayhack.doctorcare.databinding.FragmentCreatePatientBinding
import com.holidayhack.doctorcare.databinding.FragmentHomeBinding
import com.holidayhack.doctorcare.databinding.FragmentViewDoctorProfileBinding


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPatientBtn.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCreatePatientFragment()
            )
        }

        binding.searchPatientBtn.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchPatientFragment()
            )
        }
    }

}