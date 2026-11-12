# 📱 TO-DO List Application (Task Flow)

### Smart Task Management with Reminders, Progress Tracking & SQLite

A modern, feature-rich Android TO-DO List application developed using **Kotlin, Material Design 3, ViewBinding, and SQLite Database**. Task Flow helps users organize daily activities, manage priorities, track completion progress, and receive scheduled reminders through Android system notifications.

---

## 🎯 Aim

To design, develop, and deploy a modern Android application titled **TO-DO List (Task Flow)** using Kotlin, Material Design 3, ViewBinding, and SQLite Database. The application manages tasks locally, provides real-time priority filtering, tracks task completion progress, and delivers exact-time alarm reminders along with automatic daily pending and overdue task notifications.

---

## 📝 Project Description

Task Flow is an offline task management application that demonstrates how Android applications handle local data persistence, fragment navigation, task scheduling, and system notifications.

The application follows a **Single Activity Architecture**, where `MainActivity` hosts three dedicated fragments through a `BottomNavigationView`.

### Main Application Modules

| Module | Description |
|---|---|
| 📊 Dashboard Fragment | Displays task analytics, active tasks, completed tasks, total tasks, and pending task previews. |
| 📝 To-Do List Fragment | Displays pending tasks with priority badges, category tags, due dates, and interactive filters. |
| 📜 History Fragment | Displays completed tasks, completion percentage, timeframe statistics, and restore/delete options. |
| ⏰ Reminder & Digest Engine | Schedules individual task alarms and daily pending/overdue task notifications. |

---

## ✨ Features

| Feature | Description |
|---|---|
| 📊 Dashboard Analytics | Real-time counters for active, completed, and total tasks. |
| 📝 Task Management (CRUD) | Create, view, edit, delete, and complete tasks. |
| 🏷️ Priority Management | High, Medium, and Low priority with color-coded badges. |
| 📂 Category Management | Organize tasks into Work, Personal, Shopping, Finance, and other categories. |
| 🔍 Interactive Filtering | Filter tasks using All, Today, and Upcoming filter chips. |
| ⏰ Exact Alarm Reminders | Schedule individual reminders using TimePickerDialog and AlarmManager. |
| 📋 Daily Digest Notifications | Receive summaries of pending and overdue tasks. |
| 📜 Completion History | Maintain completed task records with strikethrough styling. |
| 📈 Progress Tracking | Display overall completion percentage and timeframe statistics. |
| 💾 SQLite Persistence | Store task information locally without requiring an internet connection. |
| 🔄 Reboot Recovery | Reschedule active task reminders and daily digest alarms after device reboot. |
| 🔔 Test Notification | Verify notification functionality directly from the dashboard. |

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Android Studio | Android application development environment |
| Kotlin | Application programming language |
| XML | User interface design |
| Material Design 3 | Modern Android UI components and styling |
| ViewBinding | Type-safe and null-safe view interaction |
| SQLite | Local database management |
| SQLiteOpenHelper | Database creation, upgrades, and CRUD operations |
| AlarmManager | Scheduling exact-time reminders |
| NotificationManager | Displaying system notifications |
| BroadcastReceiver | Handling alarm and device reboot events |
| RecyclerView | Displaying task lists efficiently |
| Android SDK (API 37) | Android application development framework |
| JVM 11 | Kotlin application compilation target |

---

## 🧠 Concepts Implemented

### 1. Single Activity & Fragment Navigation

The application uses `MainActivity` as the main activity and manages three fragments:

- `DashboardFragment`
- `TodoListFragment`
- `HistoryFragment`

A `BottomNavigationView` allows users to switch between the three sections without repeatedly creating new activities.

**Example Kotlin Code:**

```kotlin
supportFragmentManager.beginTransaction()
    .add(R.id.fragment_container, historyFragment, "3")
    .hide(historyFragment)
    .add(R.id.fragment_container, todoListFragment, "2")
    .hide(todoListFragment)
    .add(R.id.fragment_container, dashboardFragment, "1")
    .commit()
```

### 2. Local Database Persistence (SQLite)

The application uses `SQLiteOpenHelper` to create and manage the local database.

**Database Name:** `todolist.db`

**Task Data Model:**

```kotlin
data class Task(
    val id: Long = 0L,
    val title: String,
    val description: String = "",
    val category: String = "General",
    val priority: String = "Medium",
    val dueDate: String = "Today",
    val isCompleted: Boolean = false,
    val createdTimestamp: Long = System.currentTimeMillis(),
    val completedTimestamp: Long? = null,
    val reminderTimestamp: Long? = null
)
```

**SQLite Table Schema:**

```sql
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
);
```

**Database Operations:**

| Operation | Description |
|---|---|
| INSERT | Add a new task to the database. |
| SELECT | Retrieve active and completed tasks. |
| UPDATE | Modify task details or completion status. |
| DELETE | Permanently remove a task. |

The application also supports **database version 2 schema migration**.

### 3. Alarm Scheduling & System Notifications

The application uses Android's `AlarmManager` to schedule individual task reminders.

The `setExactAndAllowWhileIdle()` method is used to schedule alarms that can trigger even when the device is in Doze mode, subject to Android system restrictions and permissions.

`TaskNotificationReceiver`, a `BroadcastReceiver`, receives alarm events and displays notifications using `NotificationManager`.

The application also supports:

- Daily digest notifications at 9:00 AM.
- App-launch pending and overdue task summaries.
- Individual task reminder notifications.
- Alarm recovery after device reboot.

### 4. System Permissions

The following permissions are declared in `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />

<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

| Permission | Purpose |
|---|---|
| `POST_NOTIFICATIONS` | Allows the application to display notifications on Android 13 and above. |
| `SCHEDULE_EXACT_ALARM` | Allows scheduling exact-time alarms, subject to Android permission requirements. |
| `RECEIVE_BOOT_COMPLETED` | Allows the application to restore scheduled reminders after device reboot. |

---

## ⚙️ Application Workflow

1. Launch the Task Flow Android application.
2. `MainActivity` initializes the notification channel.
3. The application requests notification permission on Android 13 and above.
4. The daily 9:00 AM digest alarm is scheduled.
5. The application checks for pending and overdue tasks and posts a summary when applicable.
6. The Dashboard displays live task statistics and pending task previews.
7. Tap the quick-action `+` banner to navigate directly to the To-Do List tab.
8. Tap the Floating Action Button (`+`) to open `TaskBottomSheetDialog`.
9. Enter the task title, description, priority, category, due date, and optional reminder time.
10. Click **Add Task** to save the task in SQLite and schedule its reminder.
11. Use the All, Today, and Upcoming filter chips to filter pending tasks.
12. Tap the completion checkmark to move a task into the History section.
13. View the completion percentage and timeframe statistics in History.
14. Use the **Test Notification 🔔** button to verify system notifications.

---

## 🧪 Test Cases

The following test cases are used to verify the application's functionality.

| Test Case | Scenario / Input | Expected Result |
|---|---|---|
| TC01 | Submit task form with an empty title. | Displays error message: `Title cannot be empty`. |
| TC02 | Add a task with valid details and reminder time. | Task is saved in SQLite, displayed in the To-Do list, and its alarm is scheduled. |
| TC03 | Select the Today filter chip. | Displays only tasks with the due date set to Today. |
| TC04 | Click the completion checkmark on a task. | Task moves to History and completion percentage increases. |
| TC05 | Tap the Test Notification button. | Displays an instant notification in the system notification panel. |
| TC06 | Reach the scheduled reminder time. | Displays the corresponding task reminder notification. |
| TC07 | Launch the application when pending or overdue tasks exist. | Displays a summary notification of pending and overdue tasks. |
| TC08 | Restart the Android device. | Restores eligible scheduled task reminders and the daily digest alarm. |

---

## ▶️ How to Run the Project

Follow these steps to execute the application in Android Studio.

1. Open **Android Studio**.
2. Select **Open** and choose the project directory (`ToDoList`).
3. Wait for Gradle synchronization to complete.
4. Open `app/build.gradle.kts`.
5. Verify the following configurations:

```kotlin
android {
    compileSdk = 37

    buildFeatures {
        viewBinding = true
    }
}
```

6. Connect an Android device or start an Android Emulator (API 24 or above).
7. Click the **Run** button or press `Shift + F10`.
8. Grant notification permissions when prompted.
9. Create tasks and configure their priorities, categories, due dates, and reminder times.
10. Test task filtering, completion history, and system notifications.

---

## 📂 Project Structure

```text
ToDoList/
│
├── app/
│   ├── build.gradle.kts
│   └── src/
│       └── main/
│           │
│           ├── AndroidManifest.xml
│           │
│           ├── java/com/example/todolist/
│           │   │
│           │   ├── MainActivity.kt
│           │   │
│           │   ├── adapter/
│           │   │   └── TaskAdapter.kt
│           │   │
│           │   ├── db/
│           │   │   └── TaskDatabaseHelper.kt
│           │   │
│           │   ├── dialog/
│           │   │   └── TaskBottomSheetDialog.kt
│           │   │
│           │   ├── fragment/
│           │   │   ├── DashboardFragment.kt
│           │   │   ├── TodoListFragment.kt
│           │   │   └── HistoryFragment.kt
│           │   │
│           │   ├── model/
│           │   │   └── Task.kt
│           │   │
│           │   └── notification/
│           │       ├── TaskNotificationHelper.kt
│           │       └── TaskNotificationReceiver.kt
│           │
│           └── res/
│               ├── drawable/
│               ├── layout/
│               ├── menu/
│               │   └── bottom_nav_menu.xml
│               └── values/
│                   ├── colors.xml
│                   ├── strings.xml
│                   └── themes.xml
│
├── gradle/
│   └── libs.versions.toml
│
├── build.gradle.kts
└── settings.gradle.kts
```

### Important Files

| File | Responsibility |
|---|---|
| `MainActivity.kt` | Main activity and bottom navigation management. |
| `Task.kt` | Task data model. |
| `TaskAdapter.kt` | RecyclerView adapter for displaying tasks. |
| `TaskDatabaseHelper.kt` | SQLite database operations and statistics queries. |
| `TaskBottomSheetDialog.kt` | Task creation and editing interface. |
| `DashboardFragment.kt` | Dashboard analytics and quick actions. |
| `TodoListFragment.kt` | Pending task list and interactive filters. |
| `HistoryFragment.kt` | Completed tasks and progress tracking. |
| `TaskNotificationHelper.kt` | Alarm scheduling and notification creation. |
| `TaskNotificationReceiver.kt` | Handles alarm broadcasts and device reboot events. |

---

## 📚 Learning Outcomes

After completing this project, the following Android development concepts were implemented and understood:

1. Implementing Single Activity Architecture using Android Fragments.
2. Managing navigation using `BottomNavigationView`.
3. Performing CRUD operations using SQLite and `SQLiteOpenHelper`.
4. Designing modern Android interfaces using Material Design 3.
5. Using ViewBinding for efficient view interaction.
6. Implementing RecyclerView for dynamic task lists.
7. Scheduling exact-time alarms using `AlarmManager`.
8. Creating and managing notification channels.
9. Handling Android 13+ runtime notification permissions.
10. Implementing `BroadcastReceiver` for alarm and reboot events.
11. Maintaining local offline data persistence.
12. Tracking task completion statistics and progress.

---

## ✅ Result

Successfully designed and developed the **TO-DO List (Task Flow)** Android application using Kotlin, Material Design 3, ViewBinding, and SQLite.

The application provides three main fragments: Dashboard, To-Do List, and History. It supports complete task management operations, priority-based organization, interactive filtering, completion progress tracking, exact-time alarm reminders, and automatic daily pending and overdue task notifications.

The project demonstrates practical implementation of Android application development concepts, local database management, background scheduling, and system notification handling.

---

## 👩‍💻 Developed By

| Details | Information |
|---|---|
| **Name** | Rashmi Kumari |
| **USN** | 25MCAR0222 |
| **Subject** | Android Application Development |
| **Project Name** | TO-DO List Application (Task Flow) |
| **Platform** | Android |
| **Language** | Kotlin |

---
