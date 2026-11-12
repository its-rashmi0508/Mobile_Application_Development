package com.example.todolist.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.todolist.db.TaskDatabaseHelper

class TaskNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        when (action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                // Reschedule all active reminders on phone reboot
                val dbHelper = TaskDatabaseHelper(context)
                val pendingTasks = dbHelper.getPendingTasks()
                val now = System.currentTimeMillis()

                pendingTasks.forEach { task ->
                    if (task.reminderTimestamp != null && task.reminderTimestamp > now) {
                        TaskNotificationHelper.scheduleReminder(context, task)
                    }
                }
                TaskNotificationHelper.scheduleDailyMorningDigest(context)
                TaskNotificationHelper.checkAndSendPendingTasksDigest(context)
            }
            TaskNotificationHelper.ACTION_DAILY_DIGEST -> {
                // Fire daily morning digest
                TaskNotificationHelper.checkAndSendPendingTasksDigest(context)
            }
            else -> {
                // Alarm triggered for a specific task
                val taskId = intent.getLongExtra("TASK_ID", 0L)
                val title = intent.getStringExtra("TASK_TITLE") ?: "Task Reminder"
                val description = intent.getStringExtra("TASK_DESC") ?: ""

                if (taskId != 0L) {
                    TaskNotificationHelper.showNotification(context, taskId, title, description)
                }
            }
        }
    }
}