package com.example.todolist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.MainActivity
import com.example.todolist.R
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.databinding.FragmentDashboardBinding
import com.example.todolist.db.TaskDatabaseHelper
import com.example.todolist.dialog.TaskBottomSheetDialog
import com.example.todolist.notification.TaskNotificationHelper

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: TaskDatabaseHelper
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = TaskDatabaseHelper(requireContext())

        setupRecyclerView()
        setupListeners()
        loadDashboardData()
    }

    override fun onResume() {
        super.onResume()
        loadDashboardData()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskCompletedChange = { task, isCompleted ->
                if (isCompleted) {
                    dbHelper.markTaskCompleted(task.id)
                    Toast.makeText(requireContext(), "Task completed! 🎉", Toast.LENGTH_SHORT).show()
                } else {
                    dbHelper.markTaskPending(task.id)
                }
                loadDashboardData()
            },
            onTaskEdit = { task ->
                val dialog = TaskBottomSheetDialog(taskToEdit = task) { savedTask ->
                    dbHelper.updateTask(savedTask)
                    loadDashboardData()
                }
                dialog.show(parentFragmentManager, "TaskBottomSheetDialog")
            },
            onTaskDelete = { task ->
                dbHelper.deleteTask(task.id)
                loadDashboardData()
            }
        )

        binding.rvDashboardTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
    }

    private fun setupListeners() {
        // Banner + button opens To-Do List Fragment directly
        binding.fabDashboardAdd.setOnClickListener {
            (activity as? MainActivity)?.selectTab(R.id.nav_todo)
        }

        binding.cardBannerAdd.setOnClickListener {
            (activity as? MainActivity)?.selectTab(R.id.nav_todo)
        }

        // Test Notification Button
        binding.btnTestNotification.setOnClickListener {
            TaskNotificationHelper.showTestNotification(requireContext())
            Toast.makeText(requireContext(), "Test notification sent! Check your status bar 🔔", Toast.LENGTH_SHORT).show()
        }
    }

    fun loadDashboardData() {
        val stats = dbHelper.getTaskStats()
        binding.tvActiveCount.text = stats.activeCount.toString()
        binding.tvCompletedCount.text = stats.completedCount.toString()
        binding.tvTotalCount.text = stats.totalCount.toString()

        val pendingTasks = dbHelper.getPendingTasks()
        taskAdapter.submitList(pendingTasks)

        if (pendingTasks.isEmpty()) {
            binding.tvEmptyDashboard.visibility = View.VISIBLE
            binding.rvDashboardTasks.visibility = View.GONE
        } else {
            binding.tvEmptyDashboard.visibility = View.GONE
            binding.rvDashboardTasks.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}