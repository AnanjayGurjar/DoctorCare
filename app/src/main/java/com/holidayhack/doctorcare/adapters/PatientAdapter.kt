package com.holidayhack.doctorcare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.holidayhack.doctorcare.R
import com.holidayhack.doctorcare.modals.Patient

class PatientAdapter(private var list : MutableList<Patient>, private val listener : (Long) -> Unit)
    : RecyclerView.Adapter<PatientAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from (parent.context)
            .inflate(R.layout.patients_list_items, parent, false)
        return ViewHolder(itemLayout)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.patientName)
        val id = itemView.findViewById<TextView>(R.id.patientId)

        init{
            itemView.setOnClickListener{
                list[adapterPosition].patientId?.let { it1 -> listener.invoke(it1) }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.name.text = currentItem.name
        holder.id.text = currentItem.patientId.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}