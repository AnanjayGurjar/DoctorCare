package com.holidayhack.doctorcare.ui.fragments

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.holidayhack.doctorcare.R
import com.holidayhack.doctorcare.databinding.FragmentDoctorProfileBinding
import com.holidayhack.doctorcare.databinding.FragmentViewDoctorProfileBinding
import com.holidayhack.doctorcare.modals.Doctor
import com.holidayhack.doctorcare.ui.viewModels.DoctorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import kotlin.properties.Delegates

class ViewDoctorProfileFragment : Fragment() {

    private lateinit var viewModel : DoctorViewModel
    private lateinit var binding : FragmentViewDoctorProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        binding = FragmentViewDoctorProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO){
            val doctor : Doctor? = viewModel.getDoctor()

            launch(Dispatchers.Main){
                if (doctor != null){
                    binding.doctorDegreeText.text = doctor.degrees
                    binding.doctorExperienceText.text = doctor.experience.toString()
                    binding.doctorName.text = doctor.name
                    binding.doctorEmailTextShow.text = doctor.email

                    val imgUri = getUri(doctor.profilePhoto)
                    binding.doctorPhoto.setImageURI(imgUri)
                }
            }
        }

        binding.editProfileBtn.setOnClickListener {
            findNavController().navigate(
                ViewDoctorProfileFragmentDirections.actionViewDoctorProfileFragmentToDoctorProfileFragment()
            )
        }
    }

    private fun getUri(bitmap : Bitmap): Uri {
        val output = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,output)
        val path : String = MediaStore.Images.Media.insertImage(requireActivity().contentResolver,
        bitmap, "Title", null)
        return Uri.parse(path)
    }

}