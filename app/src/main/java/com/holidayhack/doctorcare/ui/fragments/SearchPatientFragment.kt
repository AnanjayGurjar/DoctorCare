package com.holidayhack.doctorcare.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.holidayhack.doctorcare.R

class SearchPatientFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search_patient, container, false)
    }



}