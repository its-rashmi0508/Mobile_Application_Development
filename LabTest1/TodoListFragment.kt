package com.example.todolist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.databinding.FragmentTodoListBinding
import com.example.todolist.db.TaskDatabaseHelper
import com.example.todolist.dialog.TaskBottomSheetDialog
import com.example.todolist.model.Task
import com.example.todolist.notification.TaskNotificationHelper

class TodoListFragment : Fragment() {

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: TaskDatabaseHelper
    private lateinit var taskAdapter: TaskAdapter
    private var allPendingTasks: List<Task> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = TaskDatabaseHelper(requireContext())

        setupRecyclerView()
        setupFilters()
        setupListeners()
        loadTasks()
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskCompletedChange = { task, isCompleted ->
                if (isCompleted) {
                    dbHelper.markTaskCompleted(task.id)
                    TaskNotificationHelper.cancelReminder(requireContext(), task.id)
                    Toast.makeText(requireContext(), "Task completed! 🎉", Toast.LENGTH_SHORT).show()
                } else {
                    dbHelper.markTaskPending(task.id)
                    if (task.reminderTimestamp != null) {
                        TaskNotificationHelper.scheduleReminder(requireContext(), task)
                    }
                }
                loadTasks()
                TaskNotificationHelper.checkAndSendPendingTasksDigest(requireContext())
            },
            onTaskEdit = { task ->
                openTaskDialog(task)
            },
            onTaskDelete = { task ->
                dbHelper.deleteTask(task.id)
                TaskNotificationHelper.cancelReminder(requireContext(), task.id)
                Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_SHORT).show()
                loadTasks()
                TaskNotificationHelper.checkAndSendPendingTasksDigest(requireContext())
            }
        )

        binding.rvTodoTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
    }

    private fun setupFilters() {
        binding.cgFilters.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isEmpty()) return@setOnCheckedStateChangeListener

            when (checkedIds.first()) {
                R.id.chip_filter_today -> filterTasks("Today")
                R.id.chip_filter_upcoming -> filterTasks("Upcoming")
                else -> filterTasks("All")
            }
        }
    }

    private fun setupListeners() {
        binding.fabAddTask.setOnClickListener {
            openTaskDialog(null)
        }
    }

    fun loadTasks() {
        allPendingTasks = dbHelper.getPendingTasks()
        applyCurrentFilter()
    }

    private fun applyCurrentFilter() {
        when (binding.cgFilters.checkedChipId) {
            R.id.chip_filter_today -> filterTasks("Today")
            R.id.chip_filter_upcoming -> filterTasks("Upcoming")
            else -> filterTasks("All")
        }
    }

    private fun filterTasks(filter: String) {
        val filteredList = when (filter) {
            "Today" -> allPendingTasks.filter { it.dueDate.equals("Today", ignoreCase = true) }
            "Upcoming" -> allPendingTasks.filter { !it.dueDate.equals("Today", ignoreCase = true) }
            else -> allPendingTasks
        }

        taskAdapter.submitList(filteredList)

        if (filteredList.isEmpty()) {
            binding.layoutEmptyTodo.visibility = View.VISIBLE
            binding.rvTodoTasks.visibility = View.GONE
        } else {
            binding.layoutEmptyTodo.visibility = View.GONE
            binding.rvTodoTasks.visibility = View.VISIBLE
        }
    }

    private fun openTaskDialog(task: Task?) {
        val dialog = TaskBottomSheetDialog(taskToEdit = task) { savedTask ->
            if (savedTask.id == 0L) {
                val newId = dbHelper.addTask(savedTask)
                val taskWithId = savedTask.copy(id = newId)
                if (taskWithId.reminderTimestamp != null) {
                    TaskNotificationHelper.scheduleReminder(requireContext(), taskWithId)
                }
                Toast.makeText(requireContext(), "Task added", Toast.LENGTH_SHORT).show()
            } else {
                dbHelper.updateTask(savedTask)
                if (savedTask.reminderTimestamp != null) {
                    TaskNotificationHelper.scheduleReminder(requireContext(), savedTask)
                } else {
                    TaskNotificationHelper.cancelReminder(requireContext(), savedTask.id)
                }
                Toast.makeText(requireContext(), "Task updated", Toast.LENGTH_SHORT).show()
            }
            loadTasks()
            TaskNotificationHelper.checkAndSendPendingTasksDigest(requireContext())
        }
        dialog.show(parentFragmentManager, "TaskBottomSheetDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}