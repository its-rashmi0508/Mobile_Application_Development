package com.example.sniffmeapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sniffmeapp.data.NetworkClient
import com.example.sniffmeapp.data.Student
import com.example.sniffmeapp.ui.StudentAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView
    private lateinit var recyclerView: RecyclerView

    private var allStudents = listOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        progressBar = findViewById(R.id.progressBar)
        tvEmpty = findViewById(R.id.tvEmpty)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            filterAndDisplayStudents(query)
        }

        fetchStudents()
    }

    private fun fetchStudents() {
        progressBar.visibility = View.VISIBLE
        btnSearch.isEnabled = false

        lifecycleScope.launch {
            try {
                val userDtos = withContext(Dispatchers.IO) {
                    NetworkClient.apiService.getUsers()
                }
                progressBar.visibility = View.GONE
                btnSearch.isEnabled = true

                val courses = listOf("MCA", "MBA", "BCA", "MCA", "MBA", "BCA", "MCA", "MBA", "BCA", "MCA")
                allStudents = userDtos.mapIndexed { index, user ->
                    Student(
                        id = user.id,
                        name = user.name,
                        course = courses[index % courses.size],
                        email = user.email
                    )
                }

                if (allStudents.size >= 5) {
                    allStudents = allStudents.toMutableList().apply {
                        this[0] = this[0].copy(name = "Rahul", course = "MCA", email = "rahul@gmail.com")
                        this[1] = this[1].copy(name = "Rashmi", course = "MCA", email = "rashmi@gmail.com")
                        this[2] = this[2].copy(name = "Priya", course = "MBA", email = "priya@gmail.com")
                        this[3] = this[3].copy(name = "Amit", course = "BCA", email = "amit@gmail.com")
                        this[4] = this[4].copy(name = "Sneha", course = "MCA", email = "sneha@gmail.com")
                    }
                }

                filterAndDisplayStudents(etSearch.text.toString().trim())
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                btnSearch.isEnabled = true
                Toast.makeText(this@MainActivity, "Error fetching data: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun filterAndDisplayStudents(query: String) {
        val filtered = if (query.isEmpty()) {
            allStudents
        } else {
            allStudents.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.course.contains(query, ignoreCase = true)
            }
        }

        if (filtered.isEmpty()) {
            recyclerView.visibility = View.GONE
            tvEmpty.visibility = View.VISIBLE
        } else {
            tvEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerView.adapter = StudentAdapter(filtered)
        }
    }
}
