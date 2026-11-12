package com.example.todolist.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolist.model.Task

data class TaskStats(
    val activeCount: Int,
    val completedCount: Int,
    val totalCount: Int,
    val todayCompletedCount: Int = 0,
    val thisWeekCompletedCount: Int = 0,
    val thisMonthCompletedCount: Int = 0
) {
    val completionPercentage: Int
        get() = if (totalCount > 0) ((completedCount.toDouble() / totalCount) * 100).toInt() else 0
}

class TaskDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todolist.db"
        private const val DATABASE_VERSION = 2

        private const val TABLE_TASKS = "tasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_PRIORITY = "priority"
        private const val COLUMN_DUE_DATE = "due_date"
        private const val COLUMN_IS_COMPLETED = "is_completed"
        private const val COLUMN_CREATED_TIMESTAMP = "created_timestamp"
        private const val COLUMN_COMPLETED_TIMESTAMP = "completed_timestamp"
        private const val COLUMN_REMINDER_TIMESTAMP = "reminder_timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_TASKS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_PRIORITY TEXT,
                $COLUMN_DUE_DATE TEXT,
                $COLUMN_IS_COMPLETED INTEGER DEFAULT 0,
                $COLUMN_CREATED_TIMESTAMP INTEGER,
                $COLUMN_COMPLETED_TIMESTAMP INTEGER,
                $COLUMN_REMINDER_TIMESTAMP INTEGER
            )
        """.trimIndent()
        db.execSQL(createTableQuery)

        insertSampleData(db)
    }

    private fun insertSampleData(db: SQLiteDatabase) {
        val now = System.currentTimeMillis()
        val sampleTasks = listOf(
            ContentValues().apply {
                put(COLUMN_TITLE, "Grocery Buy")
                put(COLUMN_DESCRIPTION, "Milk, bread, eggs, and vegetables")
                put(COLUMN_CATEGORY, "Shopping")
                put(COLUMN_PRIORITY, "High")
                put(COLUMN_DUE_DATE, "Today")
                put(COLUMN_IS_COMPLETED, 0)
                put(COLUMN_CREATED_TIMESTAMP, now)
            },
            ContentValues().apply {
                put(COLUMN_TITLE, "Client Meeting")
                put(COLUMN_DESCRIPTION, "Discuss Q3 roadmap with marketing team")
                put(COLUMN_CATEGORY, "Work")
                put(COLUMN_PRIORITY, "Medium")
                put(COLUMN_DUE_DATE, "Overdue")
                put(COLUMN_IS_COMPLETED, 0)
                put(COLUMN_CREATED_TIMESTAMP, now - 86400000)
            },
            ContentValues().apply {
                put(COLUMN_TITLE, "Daughter School")
                put(COLUMN_DESCRIPTION, "4:00 PM I have to go to school pickup")
                put(COLUMN_CATEGORY, "Personal")
                put(COLUMN_PRIORITY, "Low")
                put(COLUMN_DUE_DATE, "Today")
                put(COLUMN_IS_COMPLETED, 0)
                put(COLUMN_CREATED_TIMESTAMP, now)
            },
            ContentValues().apply {
                put(COLUMN_TITLE, "Pay Rent")
                put(COLUMN_DESCRIPTION, "Transfer June rent via banking app")
                put(COLUMN_CATEGORY, "Finance")
                put(COLUMN_PRIORITY, "High")
                put(COLUMN_DUE_DATE, "Today")
                put(COLUMN_IS_COMPLETED, 0)
                put(COLUMN_CREATED_TIMESTAMP, now)
            }
        )

        sampleTasks.forEach { values ->
            db.insert(TABLE_TASKS, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_TASKS ADD COLUMN $COLUMN_REMINDER_TIMESTAMP INTEGER")
        }
    }

    fun addTask(task: Task): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_DESCRIPTION, task.description)
            put(COLUMN_CATEGORY, task.category)
            put(COLUMN_PRIORITY, task.priority)
            put(COLUMN_DUE_DATE, task.dueDate)
            put(COLUMN_IS_COMPLETED, if (task.isCompleted) 1 else 0)
            put(COLUMN_CREATED_TIMESTAMP, task.createdTimestamp)
            put(COLUMN_COMPLETED_TIMESTAMP, task.completedTimestamp)
            put(COLUMN_REMINDER_TIMESTAMP, task.reminderTimestamp)
        }
        return db.insert(TABLE_TASKS, null, values)
    }

    fun updateTask(task: Task): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_DESCRIPTION, task.description)
            put(COLUMN_CATEGORY, task.category)
            put(COLUMN_PRIORITY, task.priority)
            put(COLUMN_DUE_DATE, task.dueDate)
            put(COLUMN_IS_COMPLETED, if (task.isCompleted) 1 else 0)
            put(COLUMN_COMPLETED_TIMESTAMP, task.completedTimestamp)
            put(COLUMN_REMINDER_TIMESTAMP, task.reminderTimestamp)
        }
        return db.update(TABLE_TASKS, values, "$COLUMN_ID = ?", arrayOf(task.id.toString()))
    }

    fun deleteTask(taskId: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_TASKS, "$COLUMN_ID = ?", arrayOf(taskId.toString()))
    }

    fun markTaskCompleted(taskId: Long): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IS_COMPLETED, 1)
            put(COLUMN_COMPLETED_TIMESTAMP, System.currentTimeMillis())
        }
        return db.update(TABLE_TASKS, values, "$COLUMN_ID = ?", arrayOf(taskId.toString()))
    }

    fun markTaskPending(taskId: Long): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IS_COMPLETED, 0)
            put(COLUMN_COMPLETED_TIMESTAMP, null as Long?)
        }
        return db.update(TABLE_TASKS, values, "$COLUMN_ID = ?", arrayOf(taskId.toString()))
    }

    fun getPendingTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_TASKS,
            null,
            "$COLUMN_IS_COMPLETED = 0",
            null,
            null,
            null,
            "$COLUMN_ID DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                tasks.add(parseTaskFromCursor(it))
            }
        }
        return tasks
    }

    fun getCompletedTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_TASKS,
            null,
            "$COLUMN_IS_COMPLETED = 1",
            null,
            null,
            null,
            "$COLUMN_COMPLETED_TIMESTAMP DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                tasks.add(parseTaskFromCursor(it))
            }
        }
        return tasks
    }

    fun getTaskStats(): TaskStats {
        val db = readableDatabase
        var activeCount = 0
        var completedCount = 0
        var todayCompleted = 0
        var thisWeekCompleted = 0
        var thisMonthCompleted = 0

        val now = System.currentTimeMillis()
        val oneDayMillis = 24 * 60 * 60 * 1000L
        val sevenDaysMillis = 7 * oneDayMillis
        val thirtyDaysMillis = 30 * oneDayMillis

        val cursor = db.rawQuery(
            "SELECT $COLUMN_IS_COMPLETED, $COLUMN_COMPLETED_TIMESTAMP FROM $TABLE_TASKS",
            null
        )

        cursor.use {
            val isCompletedIdx = it.getColumnIndexOrThrow(COLUMN_IS_COMPLETED)
            val completedTimeIdx = it.getColumnIndexOrThrow(COLUMN_COMPLETED_TIMESTAMP)

            while (it.moveToNext()) {
                val isCompleted = it.getInt(isCompletedIdx) == 1
                if (isCompleted) {
                    completedCount++
                    val compTime = if (!it.isNull(completedTimeIdx)) it.getLong(completedTimeIdx) else 0L
                    val diff = now - compTime
                    if (diff in 0..oneDayMillis) todayCompleted++
                    if (diff in 0..sevenDaysMillis) thisWeekCompleted++
                    if (diff in 0..thirtyDaysMillis) thisMonthCompleted++
                } else {
                    activeCount++
                }
            }
        }

        val totalCount = activeCount + completedCount
        return TaskStats(
            activeCount = activeCount,
            completedCount = completedCount,
            totalCount = totalCount,
            todayCompletedCount = todayCompleted,
            thisWeekCompletedCount = thisWeekCompleted,
            thisMonthCompletedCount = thisMonthCompleted
        )
    }

    private fun parseTaskFromCursor(cursor: Cursor): Task {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)) ?: ""
        val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)) ?: "General"
        val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)) ?: "Medium"
        val dueDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE)) ?: "Today"
        val isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COMPLETED)) == 1
        val createdTimestamp = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CREATED_TIMESTAMP))
        val completedTimestamp = if (!cursor.isNull(cursor.getColumnIndexOrThrow(COLUMN_COMPLETED_TIMESTAMP))) {
            cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_COMPLETED_TIMESTAMP))
        } else null
        val reminderTimestamp = if (!cursor.isNull(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_TIMESTAMP))) {
            cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_TIMESTAMP))
        } else null

        return Task(
            id = id,
            title = title,
            description = description,
            category = category,
            priority = priority,
            dueDate = dueDate,
            isCompleted = isCompleted,
            createdTimestamp = createdTimestamp,
            completedTimestamp = completedTimestamp,
            reminderTimestamp = reminderTimestamp
        )
    }
}