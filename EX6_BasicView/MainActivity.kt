package com.example.basicviewsapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connect XML Views
        val etName = findViewById<EditText>(R.id.etName)
        val etUsn = findViewById<EditText>(R.id.etUsn)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        val cbCoding = findViewById<CheckBox>(R.id.cbCoding)
        val cbSports = findViewById<CheckBox>(R.id.cbSports)
        val cbMusic = findViewById<CheckBox>(R.id.cbMusic)

        val switchNotifications =
            findViewById<Switch>(R.id.switchNotifications)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        // Submit Button Click
        btnSubmit.setOnClickListener {

            val name = etName.text.toString().trim()
            val usn = etUsn.text.toString().trim()

            // Validation
            if (name.isEmpty() || usn.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter Name and USN",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Get selected gender
            val selectedGenderId = radioGroup.checkedRadioButtonId

            val gender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else {
                "Not Selected"
            }

            // Get selected interests
            val interests = mutableListOf<String>()

            if (cbCoding.isChecked) interests.add("Coding")
            if (cbSports.isChecked) interests.add("Sports")
            if (cbMusic.isChecked) interests.add("Music")

            // Notification status
            val notificationStatus =
                if (switchNotifications.isChecked) "Enabled" else "Disabled"

            // Display result
            val message = """
                Name: $name
                USN: $usn
                Gender: $gender
                Interests: ${interests.joinToString(", ")}
                Notifications: $notificationStatus
            """.trimIndent()

            Toast.makeText(
                this,
                "Form Submitted Successfully!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}