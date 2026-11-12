📱 Experiment / Project: TO-DO List Application (Task Flow)
🎯 Aim
To design, develop, and deploy a modern, feature-rich Android application titled TO-DO List (Task Flow) using Kotlin, Material Design 3, ViewBinding, and SQLite Database. The application manages tasks locally, offers real-time priority filtering, progress tracking, and delivers exact time alarm reminders along with automatic daily pending and overdue task notifications.
📝 Description
This project demonstrates how an Android application manages user task data, performs local offline persistence using an embedded SQLite database, and schedules system notifications using AlarmManager and NotificationManager.
The application features a single-activity architecture (MainActivity) hosting three dedicated fragments navigated via a BottomNavigationView:
1.
Dashboard Fragment: Displays high-level analytics (Active Tasks, Completed Tasks, Total Tasks), a quick-action welcome card with a + button that switches directly to the To-Do list tab, and a live preview of pending tasks.
2.
To-Do List Fragment: Displays all pending tasks with priority badges (High, Medium, Low), category tags, and due dates. Includes filter chips (All, Today, Upcoming), a Floating Action Button (+) to create/edit tasks via a modal TaskBottomSheetDialog, and checkmarks to complete tasks.
3.
History Fragment: Archive of completed tasks with strikethrough styling, an overall completion progress bar (%), and timeframe metrics (Today, This Week, This Month) with options to restore or permanently delete tasks.
4.
Task Reminder & Digest Engine: Schedules exact time alarms for individual tasks and automatically checks/posts daily 9:00 AM & app-launch digest notifications for pending and overdue tasks.
✨ Features
•
📊 Dashboard Analytics: Real-time counter cards for active, completed, and total tasks with a quick-jump + banner.
•
📝 Task Management (CRUD): Create, view, edit, delete, and mark tasks complete with local database persistence.
•
🏷️ Priority & Category Badges: Color-coded badges for High (Red), Medium (Yellow), and Low (Blue) priority, alongside category tags (e.g. Work, Personal, Shopping, Finance).
•
🔍 Interactive Filtering: Filter pending tasks dynamically by All, Today, or Upcoming.
•
⏰ Exact Alarm Notifications: Set specific reminder times using TimePickerDialog and AlarmManager.
•
📋 Automatic Pending & Overdue Digest: Automatically posts a system notification summary whenever overdue or pending tasks exist.
•
📜 Completion History & Progress Bar: Visual progress indicator bar with completion percentage and timeframe stats (Today, This Week, This Month).
•
💾 Native SQLite Persistence: Local offline database storage (SQLiteOpenHelper) with version 2 schema migration support.
•
🔄 Reboot Recovery: Automatically reschedules active alarms and daily morning digests upon device reboot.
🛠️ Technologies Used
Technology
Purpose
Android Studio
Development environmentKotlin
Application programming language (JVM 11)
XML & Material Design 3
User interface design & styling
ViewBinding
Type-safe and null-safe UI binding
SQLite (SQLiteOpenHelper)
Embedded relational database for offline storage
AlarmManager & Notifications
Scheduling exact time alarms and posting system notifications
Android SDK (API 37)
Android application development framework
🧠 Concepts Implemented
1. Single Activity & Fragment Navigation
The app uses MainActivity as a single host container managing three top-level fragments (DashboardFragment, TodoListFragment, HistoryFragment) with a BottomNavigationView.
Kotlin
supportFragmentManager.beginTransaction()
    .add(R.id.fragment_container, historyFragment, "3").hide(historyFragment)
    .add(R.id.fragment_container, todoListFragment, "2").hide(todoListFragment)
    .add(R.id.fragment_container, dashboardFragment, "1")
    .commit()
   2. Local Database Persistence (SQLite)
Uses SQLiteOpenHelper to store task records. Example data model representation:
Kotlin
data class Task(
    val id: Long = 0L,
    val title: String,
    val description: String = "",
    val category: String = "General",
    val priority: String = "Medium", // High, Medium, Low
    val dueDate: String = "Today",
    val isCompleted: Boolean = false,
    val createdTimestamp: Long = System.currentTimeMillis(),
    val completedTimestamp: Long? = null,
    val reminderTimestamp: Long? = null
)
SQLite Table Schema (todolist.db):
SQL
CREATE TABLE tasks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    description TEXT,
    category TEXT,
    priority TEXT,
    due_date TEXT,
    is_completed INTEGER DEFAULT 0,
    created_timestamp INTEGER,
    completed_timestamp INTEGER,
    reminder_timestamp INTEGER
)
3. Alarm Scheduling & System Notifications
Uses AlarmManager to schedule exact alarms (setExactAndAllowWhileIdle) and TaskNotificationReceiver (BroadcastReceiver) to display notifications.
4. System Permissions (AndroidManifest.xml)
5. <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
⚙️ Application Workflow
1.
Launch the Android application.
2.
MainActivity initializes the Task Reminders notification channel, requests runtime notification permission (POST_NOTIFICATIONS) on Android 13+, schedules the daily 9:00 AM digest alarm, and posts a pending tasks summary if active tasks exist.
3.
The Dashboard displays live task metrics and current pending task previews.
4.
Tapping the banner + button switches directly to the To-Do List tab.
5.
Tap the Floating Action Button (+) to open TaskBottomSheetDialog.
6.
Enter Task Title, Description, Priority (High/Medium/Low), Category, Due Date, and optional Reminder Time via TimePickerDialog.
7.
Click Add Task; the record is inserted into SQLite and an alarm is scheduled.
8.
Filter tasks using chips (All, Today, Upcoming).
9.
Tap the checkmark on a task card to mark it complete; the task moves to the History tab, cancels its pending reminder, and updates completion progress.
10.
Tap Test Notification 🔔 on the Dashboard at any time to verify system notifications.
🧪 Test Cases
Test Case
Scenario / Input
Expected Result
TC01
Empty title in task form
Displays error: "Title cannot be empty"
TC02
Add task with valid details & time
Task saved to SQLite, displayed in To-Do list, alarm scheduled
TC03
Select filter chip "Today"
Displays only tasks with due date set to "Today"
TC04
Click complete checkmark on task
Task moves to History tab, overall completion % increases
TC05
Tap "Test Notification 🔔" button
Triggers instant test notification in system status bar
TC06
Alarm time reached / App startup
Posts notification listing task title, description, or overdue summary
▶️ How to Run the Project
1.
Open Android Studio.
2.
Select Open and choose the project directory (ToDoList).
3.
Wait for Gradle synchronization to complete.
4.
Verify that app/build.gradle.kts uses compileSdk = 37 and viewBinding = true.
5.
Connect an Android device or start an Android Emulator (API 24+).
6.
Click the Run button (or press Shift + F10).
7.
Grant notification permissions when prompted.
8.
Create tasks, set reminder times, test filtering, and verify completion history.
📂 Project Structure
ToDoList/
│
├── app/
│   ├── build.gradle.kts                          # Module build script (ViewBinding, compileSdk 37)
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml               # App manifest, permissions & broadcast receiver
│           ├── java/com/example/todolist/
│           │   ├── MainActivity.kt               # Main activity with BottomNavigationView
│           │   ├── adapter/
│           │   │   └── TaskAdapter.kt            # RecyclerView ListAdapter for task items
│           │   ├── db/
│           │   │   └── TaskDatabaseHelper.kt    # SQLite database helper (CRUD & stats queries)
│           │   ├── dialog/
│           │   │   └── TaskBottomSheetDialog.kt # Modal dialog with DatePicker & TimePicker
│           │   ├── fragment/
│           │   │   ├── DashboardFragment.kt      # Fragment 1: Analytics & quick + banner
│           │   │   ├── TodoListFragment.kt       # Fragment 2: Active tasks & filter chips
│           │   │   └── HistoryFragment.kt        # Fragment 3: Completed tasks & progress bar
│           │   ├── model/
│           │   │   └── Task.kt                   # Task data class
│           │   └── notification/
│           │       ├── TaskNotificationHelper.kt # Alarm scheduling & notification builder
│           │       └── TaskNotificationReceiver.kt # Broadcast receiver for alarms & boot events
│           └── res/
│               ├── drawable/                     # Icons & shape backgrounds
│               ├── layout/                       # Activity, Fragment & Item XML layouts
│               ├── menu/
│               │   └── bottom_nav_menu.xml       # Bottom navigation bar menu
│               └── values/
│                   ├── colors.xml                # Palette & priority badge colors
│                   ├── strings.xml               # Translatable strings
│                   └── themes.xml                # Material 3 application theme
│
├── gradle/
│   └── libs.versions.toml                        # Version catalog
├── build.gradle.kts                              # Project build script
└── settings.gradle.kts                           # Project settings
📚 Learning Outcomes
After completing this project, the following key Android development concepts were mastered:
•
Implementing a single-activity architecture with Jetpack Fragments and BottomNavigationView.
•
Managing offline database operations (CRUD) using native SQLite (SQLiteOpenHelper).
•
Utilizing Material Design 3 UI components (Cards, Chips, FloatingActionButton, BottomSheetDialogFragment, LinearProgressIndicator).
•
Enabling ViewBinding for type-safe and performant layout interactions.
•
Scheduling exact time alarms using AlarmManager and handling broadcast events via BroadcastReceiver.
•
Creating Notification Channels and handling Android 13+ runtime permissions (POST_NOTIFICATIONS).
•
Restoring scheduled background alarms after device reboot (RECEIVE_BOOT_COMPLETED).
✅ Result
Successfully developed the TO-DO List (Task Flow) Android application featuring three core fragments, SQLite database persistence, interactive priority filtering, completion statistics, exact time alarm reminders, and automatic daily pending/overdue task notifications.
👩‍💻 Developed By
Name: Rashmi Kumari
USN: 25MCAR0222
Subject: Android Application Development
