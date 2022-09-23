package com.holidayhack.doctorcare.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.firebase.auth.FirebaseAuth
import com.holidayhack.doctorcare.databinding.FragmentDoctorProfileBinding
import com.holidayhack.doctorcare.modals.Doctor
import com.holidayhack.doctorcare.ui.viewModels.DoctorViewModel
import kotlinx.coroutines.launch

const val PICK_IMAGE = 100
class DoctorProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentDoctorProfileBinding
    private lateinit var viewModel: DoctorViewModel
    private lateinit var bitmapImg: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(DoctorViewModel::class.java)
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

        binding.saveDoctorProfileBtn.setOnClickListener {
            saveDoctor()
            findNavController().navigate(
                DoctorProfileFragmentDirections.actionDoctorProfileFragmentToViewDoctorProfileFragment()
            )
        }

        binding.profilePic.setOnClickListener {
            pickImage()
        }
    }

    private fun pickImage() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"

        val chooserIntent = Intent.createChooser(getIntent, "Select Application")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK || resultCode == PICK_IMAGE) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            Log.d("TAG", "onActivityResult: ${uri.toString()}")

            lifecycleScope.launch{
                bitmapImg = getBitmap(uri)
            }
            binding.profilePic.setImageURI(uri)
        }else{
            Log.d("TAG", "onActivityResult: error")

        }
    }

    private suspend fun getBitmap(uri: Any): Bitmap {
        val loader = ImageLoader(requireContext())
        val request: ImageRequest = ImageRequest.Builder(requireContext())
            .data(uri)
            .build()

        val result: Drawable = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun saveDoctor() {
        val name = binding.etName.editText!!.text.toString()
        val degree = binding.etDegrees.editText!!.text.toString()
        val experiance = binding.etExperience.editText!!.text.toString()
        val photo: Bitmap = bitmapImg

        val doctor = Doctor(name, photo, auth.currentUser?.email!!, degree, experiance.toInt())

        viewModel.saveDoctor(doctor)
    }
}
