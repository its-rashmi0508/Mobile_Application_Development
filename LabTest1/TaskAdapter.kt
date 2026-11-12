package com.example.todolist.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.model.Task

class TaskAdapter(
    private val onTaskCompletedChange: (Task, Boolean) -> Unit,
    private val onTaskEdit: (Task) -> Unit,
    private val onTaskDelete: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            val context = binding.root.context

            binding.tvTitle.text = task.title

            if (task.description.isBlank()) {
                binding.tvDescription.visibility = View.GONE
            } else {
                binding.tvDescription.visibility = View.VISIBLE
                binding.tvDescription.text = task.description
            }

            // Category
            binding.tvCategory.text = task.category

            // Priority setup
            setupPriorityBadge(context, task.priority)

            // Due Date
            binding.tvDueDate.text = task.dueDate

            // Completed vs Active state styling
            if (task.isCompleted) {
                binding.tvTitle.paintFlags = binding.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvDescription.paintFlags = binding.tvDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.btnComplete.setImageResource(R.drawable.ic_check_circle)
                binding.btnComplete.setColorFilter(ContextCompat.getColor(context, R.color.success))
                binding.cardTask.alpha = 0.75f
            } else {
                binding.tvTitle.paintFlags = binding.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvDescription.paintFlags = binding.tvDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.btnComplete.setImageResource(R.drawable.ic_check_circle)
                binding.btnComplete.setColorFilter(ContextCompat.getColor(context, R.color.text_muted))
                binding.cardTask.alpha = 1.0f
            }

            // Complete button click
            binding.btnComplete.setOnClickListener {
                onTaskCompletedChange(task, !task.isCompleted)
            }

            // More Options popup menu
            binding.btnMenu.setOnClickListener { view ->
                showPopupMenu(view, context, task)
            }
        }

        private fun setupPriorityBadge(context: Context, priority: String) {
            binding.tvPriority.text = priority
            when (priority.lowercase()) {
                "high" -> {
                    binding.tvPriority.setBackgroundResource(R.drawable.bg_chip_priority)
                    binding.tvPriority.setTextColor(ContextCompat.getColor(context, R.color.priority_high_text))
                }
                "medium" -> {
                    binding.tvPriority.setBackgroundResource(R.drawable.bg_chip_priority)
                    binding.tvPriority.setBackgroundColor(ContextCompat.getColor(context, R.color.priority_medium_bg))
                    binding.tvPriority.setTextColor(ContextCompat.getColor(context, R.color.priority_medium_text))
                }
                else -> { // Low or default
                    binding.tvPriority.setBackgroundResource(R.drawable.bg_chip_priority)
                    binding.tvPriority.setBackgroundColor(ContextCompat.getColor(context, R.color.priority_low_bg))
                    binding.tvPriority.setTextColor(ContextCompat.getColor(context, R.color.priority_low_text))
                }
            }
        }

        private fun showPopupMenu(view: View, context: Context, task: Task) {
            val popup = PopupMenu(context, view)
            if (task.isCompleted) {
                popup.menu.add(0, 1, 0, "Mark as Pending")
            } else {
                popup.menu.add(0, 2, 0, "Edit Task")
                popup.menu.add(0, 3, 1, "Mark as Completed")
            }
            popup.menu.add(0, 4, 2, "Delete Task")

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    1 -> {
                        onTaskCompletedChange(task, false)
                        true
                    }
                    2 -> {
                        onTaskEdit(task)
                        true
                    }
                    3 -> {
                        onTaskCompletedChange(task, true)
                        true
                    }
                    4 -> {
                        onTaskDelete(task)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}