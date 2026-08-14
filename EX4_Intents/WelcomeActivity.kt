package com.example.loginapp

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var tvWelcome: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        tvWelcome = findViewById(R.id.tvWelcome)

        // Get username from MainActivity
        val username = intent.getStringExtra("username")

        // Read saved password
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        val password = sharedPreferences.getString("password", "No Password")

        // Display username and password
        tvWelcome.text = "Welcome $username\n\nSaved Password: $password"
    }
}