package com.holidayhack.doctorcare.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ImageReader
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.request.SuccessResult
import com.github.drjacky.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.holidayhack.doctorcare.R
import com.holidayhack.doctorcare.databinding.FragmentDoctorProfileBinding
import com.holidayhack.doctorcare.modals.Doctor
import com.holidayhack.doctorcare.ui.viewModels.DoctorViewModel
import kotlinx.coroutines.launch
import kotlin.math.exp


class DoctorProfileFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding : FragmentDoctorProfileBinding
    private lateinit var viewModel : DoctorViewModel
    private lateinit var bitmapImg : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDoctorProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveDoctorProfileBtn.setOnClickListener{
            saveDoctor()
        }

        binding.profilePic.setOnClickListener{
           pickImage()
        }
    }

    private fun pickImage(){
        ImagePicker.with(requireActivity())
            .createIntent()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!
                lifecycleScope.launch {
                    bitmapImg = getBitmap(uri)
                }
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }

    }

    private suspend fun getBitmap(uri: Any) : Bitmap {
        val loader : ImageLoader = ImageLoader(requireContext())
        val request : ImageRequest = ImageRequest.Builder(requireContext())
            .data(uri)
            .build()

        val result : Drawable = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }


    private fun saveDoctor() {
        val name = binding.etName.toString()
        val degree = binding.etDegrees.toString()
        val experiance = binding.etExperience.toString()
        val photo : Bitmap

        val doctor : Doctor = Doctor(name, bitmapImg, auth.currentUser?.email!!, degree, experiance.toInt() )

        viewModel.saveDoctor(doctor)
     }
}
