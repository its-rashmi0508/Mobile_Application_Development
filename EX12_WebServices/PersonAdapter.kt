package com.example.sniffmeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sniffmeapp.R
import com.example.sniffmeapp.data.Person

class PersonAdapter(private val persons: List<Person>) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvHeight: TextView = itemView.findViewById(R.id.tvHeight)
        val tvMass: TextView = itemView.findViewById(R.id.tvMass)
        val tvGender: TextView = itemView.findViewById(R.id.tvGender)
        val tvBirthYear: TextView = itemView.findViewById(R.id.tvBirthYear)
        val tvEyeColor: TextView = itemView.findViewById(R.id.tvEyeColor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = persons[position]
        val context = holder.itemView.context
        holder.tvName.text = context.getString(R.string.label_name, person.name)
        holder.tvHeight.text = context.getString(R.string.label_height, person.height)
        holder.tvMass.text = context.getString(R.string.label_mass, person.mass)
        holder.tvGender.text = context.getString(R.string.label_gender, person.gender)
        holder.tvBirthYear.text = context.getString(R.string.label_birth_year, person.birthYear)
        holder.tvEyeColor.text = context.getString(R.string.label_eye_color, person.eyeColor)
    }

    override fun getItemCount(): Int = persons.size
}
