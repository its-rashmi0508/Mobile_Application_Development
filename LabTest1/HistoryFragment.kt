package com.example.todolist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.databinding.FragmentHistoryBinding
import com.example.todolist.db.TaskDatabaseHelper

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: TaskDatabaseHelper
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = TaskDatabaseHelper(requireContext())

        setupRecyclerView()
        loadHistoryData()
    }

    override fun onResume() {
        super.onResume()
        loadHistoryData()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskCompletedChange = { task, isCompleted ->
                if (!isCompleted) {
                    dbHelper.markTaskPending(task.id)
                    Toast.makeText(requireContext(), "Task restored to active list", Toast.LENGTH_SHORT).show()
                } else {
                    dbHelper.markTaskCompleted(task.id)
                }
                loadHistoryData()
            },
            onTaskEdit = { task ->
                // Edits not typically used in history but supported
            },
            onTaskDelete = { task ->
                dbHelper.deleteTask(task.id)
                Toast.makeText(requireContext(), "Task deleted from history", Toast.LENGTH_SHORT).show()
                loadHistoryData()
            }
        )

        binding.rvHistoryTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
    }

    fun loadHistoryData() {
        val stats = dbHelper.getTaskStats()
        binding.tvOverallCompletionText.text = "${stats.completedCount} / ${stats.totalCount} tasks"
        binding.progressCompletion.progress = stats.completionPercentage
        binding.tvCompletionPercentage.text = "${stats.completionPercentage}% of all tasks completed"

        binding.tvTodayCount.text = stats.todayCompletedCount.toString()
        binding.tvWeekCount.text = stats.thisWeekCompletedCount.toString()
        binding.tvMonthCount.text = stats.thisMonthCompletedCount.toString()

        val completedTasks = dbHelper.getCompletedTasks()
        taskAdapter.submitList(completedTasks)

        if (completedTasks.isEmpty()) {
            binding.layoutEmptyHistory.visibility = View.VISIBLE
            binding.rvHistoryTasks.visibility = View.GONE
        } else {
            binding.layoutEmptyHistory.visibility = View.GONE
            binding.rvHistoryTasks.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}