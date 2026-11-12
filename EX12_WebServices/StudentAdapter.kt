package com.example.sniffmeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sniffmeapp.R
import com.example.sniffmeapp.data.Student

class StudentAdapter(private val students: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvStudentName)
        val tvId: TextView = itemView.findViewById(R.id.tvStudentId)
        val tvCourse: TextView = itemView.findViewById(R.id.tvStudentCourse)
        val tvEmail: TextView = itemView.findViewById(R.id.tvStudentEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvName.text = "Name: ${student.name}"
        holder.tvId.text = "ID: ${student.id}"
        holder.tvCourse.text = "Course: ${student.course}"
        holder.tvEmail.text = "Email: ${student.email}"
    }

    override fun getItemCount(): Int = students.size
}
