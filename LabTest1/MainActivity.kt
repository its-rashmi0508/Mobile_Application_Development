package com.example.todolist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.fragment.DashboardFragment
import com.example.todolist.fragment.HistoryFragment
import com.example.todolist.fragment.TodoListFragment
import com.example.todolist.notification.TaskNotificationHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val dashboardFragment by lazy { DashboardFragment() }
    private val todoListFragment by lazy { TodoListFragment() }
    private val historyFragment by lazy { HistoryFragment() }

    private var activeFragment: Fragment = dashboardFragment

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Post pending tasks digest when permission is granted
                TaskNotificationHelper.checkAndSendPendingTasksDigest(this)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Initialize Notification Channel & Schedule Daily 9 AM Digest Alarm
        TaskNotificationHelper.createNotificationChannel(this)
        TaskNotificationHelper.scheduleDailyMorningDigest(this)

        // Request POST_NOTIFICATIONS permission for Android 13+ (API 33+)
        checkNotificationPermission()

        // Automatically check and notify if there are overdue/today pending tasks
        TaskNotificationHelper.checkAndSendPendingTasksDigest(this)

        setupNavigation()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, historyFragment, "3").hide(historyFragment)
                .add(R.id.fragment_container, todoListFragment, "2").hide(todoListFragment)
                .add(R.id.fragment_container, dashboardFragment, "1")
                .commit()
            activeFragment = dashboardFragment
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun setupNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    switchFragment(dashboardFragment)
                    dashboardFragment.loadDashboardData()
                    true
                }
                R.id.nav_todo -> {
                    switchFragment(todoListFragment)
                    todoListFragment.loadTasks()
                    true
                }
                R.id.nav_history -> {
                    switchFragment(historyFragment)
                    historyFragment.loadHistoryData()
                    true
                }
                else -> false
            }
        }
    }

    private fun switchFragment(targetFragment: Fragment) {
        if (activeFragment != targetFragment) {
            supportFragmentManager.beginTransaction()
                .hide(activeFragment)
                .show(targetFragment)
                .commit()
            activeFragment = targetFragment
        }
    }

    fun selectTab(tabItemId: Int) {
        binding.bottomNavigation.selectedItemId = tabItemId
    }
}