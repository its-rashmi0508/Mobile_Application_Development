package com.example.datapersistenceapp

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // SharedPreferences Constants
    companion object {
        private const val PREF_NAME = "UserPreferences"
        private const val KEY_USERNAME = "KEY_USERNAME"
        private const val KEY_EMAIL = "KEY_EMAIL"
    }

    // SharedPreferences UI Views
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnSaveSp: Button
    private lateinit var btnLoadSp: Button
    private lateinit var btnClearSp: Button
    private lateinit var tvSpOutput: TextView

    // SQLite Helper & UI Views
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etStudentId: EditText
    private lateinit var etStudentName: EditText
    private lateinit var etStudentCourse: EditText
    private lateinit var btnInsertSqlite: Button
    private lateinit var btnViewSqlite: Button
    private lateinit var btnUpdateSqlite: Button
    private lateinit var btnDeleteSqlite: Button
    private lateinit var tvSqliteOutput: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize SharedPreferences & SQLite Database Helper
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        dbHelper = DatabaseHelper(this)

        initViews()
        setupSharedPreferencesListeners()
        setupSqliteListeners()
    }

    private fun initViews() {
        // SharedPreferences Views
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        btnSaveSp = findViewById(R.id.btnSaveSp)
        btnLoadSp = findViewById(R.id.btnLoadSp)
        btnClearSp = findViewById(R.id.btnClearSp)
        tvSpOutput = findViewById(R.id.tvSpOutput)

        // SQLite Views
        etStudentId = findViewById(R.id.etStudentId)
        etStudentName = findViewById(R.id.etStudentName)
        etStudentCourse = findViewById(R.id.etStudentCourse)
        btnInsertSqlite = findViewById(R.id.btnInsertSqlite)
        btnViewSqlite = findViewById(R.id.btnViewSqlite)
        btnUpdateSqlite = findViewById(R.id.btnUpdateSqlite)
        btnDeleteSqlite = findViewById(R.id.btnDeleteSqlite)
        tvSqliteOutput = findViewById(R.id.tvSqliteOutput)
    }

    // ------------------------------------------------------------------------
    // 1. SHARED PREFERENCES OPERATIONS
    // ------------------------------------------------------------------------
    private fun setupSharedPreferencesListeners() {
        // Save to SharedPreferences
        btnSaveSp.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (username.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please enter both Username and Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sharedPreferences.edit {
                putString(KEY_USERNAME, username)
                putString(KEY_EMAIL, email)
            }

            val savedText = "Saved Preferences:\nUsername: $username\nEmail: $email"
            tvSpOutput.text = savedText
            Toast.makeText(this, "Preferences Saved!", Toast.LENGTH_SHORT).show()
        }

        // Load from SharedPreferences
        btnLoadSp.setOnClickListener {
            val username = sharedPreferences.getString(KEY_USERNAME, null)
            val email = sharedPreferences.getString(KEY_EMAIL, null)

            if (!username.isNullOrEmpty() && !email.isNullOrEmpty()) {
                etUsername.setText(username)
                etEmail.setText(email)
                val loadedText = "Loaded Preferences:\nUsername: $username\nEmail: $email"
                tvSpOutput.text = loadedText
                Toast.makeText(this, "Preferences Loaded!", Toast.LENGTH_SHORT).show()
            } else {
                tvSpOutput.text = getString(R.string.no_prefs_found)
                Toast.makeText(this, "No saved preferences found", Toast.LENGTH_SHORT).show()
            }
        }

        // Clear SharedPreferences
        btnClearSp.setOnClickListener {
            sharedPreferences.edit {
                clear()
            }

            etUsername.text.clear()
            etEmail.text.clear()
            tvSpOutput.text = getString(R.string.sp_default_output)
            Toast.makeText(this, "Preferences Cleared!", Toast.LENGTH_SHORT).show()
        }
    }

    // ------------------------------------------------------------------------
    // 2. SQLITE DATABASE CRUD OPERATIONS
    // ------------------------------------------------------------------------
    private fun setupSqliteListeners() {
        // INSERT Data
        btnInsertSqlite.setOnClickListener {
            val name = etStudentName.text.toString().trim()
            val course = etStudentCourse.text.toString().trim()

            if (name.isEmpty() || course.isEmpty()) {
                Toast.makeText(this, "Please enter Student Name and Course", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = dbHelper.insertData(name, course)
            if (result != -1L) {
                Toast.makeText(this, "Record Inserted! (ID: $result)", Toast.LENGTH_SHORT).show()
                etStudentName.text.clear()
                etStudentCourse.text.clear()
                displayAllSqliteRecords()
            } else {
                Toast.makeText(this, "Failed to insert record", Toast.LENGTH_SHORT).show()
            }
        }

        // VIEW ALL Data
        btnViewSqlite.setOnClickListener {
            displayAllSqliteRecords()
        }

        // UPDATE Data
        btnUpdateSqlite.setOnClickListener {
            val id = etStudentId.text.toString().trim()
            val name = etStudentName.text.toString().trim()
            val course = etStudentCourse.text.toString().trim()

            if (id.isEmpty() || name.isEmpty() || course.isEmpty()) {
                Toast.makeText(this, "Please enter ID, Name, and Course to Update", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isUpdated = dbHelper.updateData(id, name, course)
            if (isUpdated) {
                Toast.makeText(this, "Record Updated Successfully!", Toast.LENGTH_SHORT).show()
                displayAllSqliteRecords()
            } else {
                Toast.makeText(this, "Failed to Update. Check ID!", Toast.LENGTH_SHORT).show()
            }
        }

        // DELETE Data
        btnDeleteSqlite.setOnClickListener {
            val id = etStudentId.text.toString().trim()

            if (id.isEmpty()) {
                Toast.makeText(this, "Please enter Student ID to Delete", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val deletedRows = dbHelper.deleteData(id)
            if (deletedRows > 0) {
                Toast.makeText(this, "Record Deleted Successfully!", Toast.LENGTH_SHORT).show()
                etStudentId.text.clear()
                displayAllSqliteRecords()
            } else {
                Toast.makeText(this, "Failed to Delete. ID not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayAllSqliteRecords() {
        val cursor = dbHelper.getAllData()
        if (cursor.count == 0) {
            tvSqliteOutput.text = getString(R.string.no_sqlite_records)
            Toast.makeText(this, "No records found", Toast.LENGTH_SHORT).show()
            return
        }

        val buffer = StringBuilder()
        buffer.append("Total Records: ${cursor.count}\n-----------------------------\n")
        while (cursor.moveToNext()) {
            val id = cursor.getString(0)
            val name = cursor.getString(1)
            val course = cursor.getString(2)
            buffer.append("ID: $id | Name: $name | Course: $course\n")
        }
        cursor.close()

        tvSqliteOutput.text = buffer.toString()
    }
}
