package com.holidayhack.doctorcare.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.holidayhack.doctorcare.R
import kotlinx.android.extensions.LayoutContainer

class PatientAdapter(private var list : MutableList<Int>)
    : RecyclerView.Adapter<PatientAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientAdapter.viewHolder {
        val itemLayout = LayoutInflater.from (parent.context)
            .inflate(R.layout.patients_list_items, parent, false)
        return viewHolder(itemLayout)
    }

    inner class viewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    }

    override fun onBindViewHolder(holder: PatientAdapter.viewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return list.size
    }
}