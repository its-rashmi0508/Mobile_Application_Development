package com.example.todolist.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.todolist.R
import com.example.todolist.databinding.DialogAddTaskBinding
import com.example.todolist.model.Task
import com.example.todolist.notification.TaskNotificationHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskBottomSheetDialog(
    private val taskToEdit: Task? = null,
    private val onTaskSaved: (Task) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DialogAddTaskBinding? = null
    private val binding get() = _binding!!

    private var selectedDueDate: String = "Today"
    private var reminderTimeMillis: Long? = null
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        if (taskToEdit != null) {
            binding.tvDialogTitle.text = getString(R.string.edit_task)
            binding.btnSave.text = getString(R.string.edit_task)
            binding.etTitle.setText(taskToEdit.title)
            binding.etDescription.setText(taskToEdit.description)
            binding.etCategory.setText(taskToEdit.category)
            binding.etDueDate.setText(taskToEdit.dueDate)
            selectedDueDate = taskToEdit.dueDate
            reminderTimeMillis = taskToEdit.reminderTimestamp

            if (reminderTimeMillis != null) {
                val timeFormat = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault())
                binding.etReminderTime.setText(timeFormat.format(reminderTimeMillis!!))
            }

            when (taskToEdit.priority.lowercase()) {
                "high" -> binding.chipHigh.isChecked = true
                "low" -> binding.chipLow.isChecked = true
                else -> binding.chipMedium.isChecked = true
            }
        } else {
            binding.tvDialogTitle.text = getString(R.string.create_new_task)
            binding.btnSave.text = getString(R.string.add_task)
            binding.chipMedium.isChecked = true
            binding.etDueDate.setText("Today")
        }
    }

    private fun setupListeners() {
        // Date Picker
        binding.etDueDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    calendar.set(Calendar.YEAR, selectedYear)
                    calendar.set(Calendar.MONTH, selectedMonth)
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
                    val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    selectedDueDate = format.format(calendar.time)
                    binding.etDueDate.setText(selectedDueDate)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        // Time Picker for Reminder
        binding.etReminderTime.setOnClickListener {
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, selectedHour, selectedMinute ->
                    val now = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                    calendar.set(Calendar.MINUTE, selectedMinute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)

                    // If selected time today has already passed, schedule for tomorrow
                    if (calendar.before(now)) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1)
                    }

                    reminderTimeMillis = calendar.timeInMillis
                    val timeFormat = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault())
                    binding.etReminderTime.setText(timeFormat.format(calendar.time))
                },
                hour,
                minute,
                false
            )
            timePickerDialog.show()
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            val category = binding.etCategory.text.toString().trim().ifEmpty { "General" }
            val dueDate = binding.etDueDate.text.toString().trim().ifEmpty { "Today" }

            if (title.isEmpty()) {
                binding.tilTitle.error = "Title cannot be empty"
                return@setOnClickListener
            } else {
                binding.tilTitle.error = null
            }

            val priority = when (binding.cgPriority.checkedChipId) {
                R.id.chip_high -> "High"
                R.id.chip_low -> "Low"
                else -> "Medium"
            }

            val task = Task(
                id = taskToEdit?.id ?: 0L,
                title = title,
                description = description,
                category = category,
                priority = priority,
                dueDate = dueDate,
                isCompleted = taskToEdit?.isCompleted ?: false,
                createdTimestamp = taskToEdit?.createdTimestamp ?: System.currentTimeMillis(),
                completedTimestamp = taskToEdit?.completedTimestamp,
                reminderTimestamp = reminderTimeMillis
            )

            if (reminderTimeMillis != null) {
                val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                Toast.makeText(
                    requireContext(),
                    "Notification set for ${timeFormat.format(reminderTimeMillis!!)}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            onTaskSaved(task)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}