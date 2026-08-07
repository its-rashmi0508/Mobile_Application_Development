# Experiment 2 - Activity Lifecycle in Android

## 📌 Aim

To understand and demonstrate the Android Activity Lifecycle by implementing all lifecycle callback methods and displaying their execution using Custom Toast messages and Logcat.

---

## 📖 Objective

The objective of this experiment is to study how an Android Activity behaves during different stages of its lifecycle. The application demonstrates each lifecycle callback and helps understand how Android manages activities while interacting with the user.

---

## 🛠️ Tools & Technologies Used

- Android Studio
- Kotlin
- Jetpack Compose
- Java (Custom Toast)
- Android SDK
- Logcat

---

## 📱 Features

- Displays **Hello World** on the screen.
- Implements all Android Activity Lifecycle methods:
  - onCreate()
  - onStart()
  - onResume()
  - onPause()
  - onStop()
  - onRestart()
  - onDestroy()
- Displays a **Custom Toast** for each lifecycle event.
- Custom Toast appears at the **top** of the screen.
- Custom Toast includes:
  - Custom background
  - Rounded corners
  - Information icon
- Displays lifecycle events in **Logcat** for debugging.

---

## 🔄 Android Activity Lifecycle

| Lifecycle Method | Description |
|------------------|-------------|
| onCreate() | Called when the activity is first created. |
| onStart() | Called when the activity becomes visible. |
| onResume() | Called when the activity starts interacting with the user. |
| onPause() | Called when another activity partially covers the current activity. |
| onStop() | Called when the activity is no longer visible. |
| onRestart() | Called after the activity has been stopped and is restarting. |
| onDestroy() | Called before the activity is destroyed. |

---

## 📂 Project Structure

```
Experiment-02-ActivityLifecycle
│
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── CustomToaster.java
│   │   │   ├── res
│   │   │   │   ├── layout
│   │   │   │   │   └── toast_layout.xml
│   │   │   │   ├── drawable
│   │   │   │   │   └── toast_background.xml
│   │   │   │   └── values
│   │   │   └── AndroidManifest.xml
│
├── gradle
├── build.gradle
├── settings.gradle
└── README.md
```

---

## ▶️ How to Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle files.
4. Connect an Android device or start an emulator.
5. Click **Run ▶️**.
6. Observe the lifecycle callbacks using:
   - Custom Toast messages
   - Logcat window

---

## 🧪 Test Cases

### Test Case 1
**Action:** Launch the application

**Expected Result:**
- onCreate()
- onStart()
- onResume()

---

### Test Case 2
**Action:** Press the Home button and reopen the app.

**Expected Result:**
- onPause()
- onStop()
- onRestart()
- onStart()
- onResume()

---

### Test Case 3
**Action:** Press the Back button.

**Expected Result:**
- onPause()
- onStop()
- onDestroy()

---
## Demo Video

https://github.com/user-attachments/assets/4116e993-7a89-4b8e-b4fc-5b7863629a96

---

## 📚 Learning Outcome

- Understood the Android Activity Lifecycle.
- Implemented lifecycle callback methods in Kotlin.
- Learned how to display messages using Custom Toasts.
- Used Logcat for monitoring lifecycle events.
- Customized Toast appearance using XML layouts and drawable resources.

---

## 👩‍💻 Developed By

**Rashmi Kumari**

MCA Student

Jain University

---

## 📄 License

This project is created for academic and educational purposes.
