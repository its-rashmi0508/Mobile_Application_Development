# 📱 Experiment 12: Access Web Services Using HTTP Requests in Android

## 🎯 Aim

To develop an Android application that accesses web services using HTTP requests, retrieves student information from an online JSON API, and displays the retrieved data in a user-friendly interface with search functionality.

## 📝 Description

This project demonstrates how an Android application communicates with a web service over the internet using HTTP requests.

The application retrieves student information from a remote API in JSON format and displays the records in the form of individual student cards.

Users can search for students using their **name or course**. The application filters the retrieved student records and displays only the matching results.

For example:
- Searching for `Rashmi` displays only Rashmi's information.
- Searching for `MCA` displays students enrolled in MCA.
- Searching for `MBA` displays students enrolled in MBA.
- Searching for an unavailable name displays a no-results message.

## ✨ Features

- 🌐 **HTTP Communication:** Connects to a web service to retrieve student data.
- 📥 **JSON Data Retrieval:** Receives student information in JSON format.
- 🔍 **Student Search:** Search students by name or course.
- 📋 **Student Information Display:** Displays student ID, name, course, and email.
- 🎨 **Simple User Interface:** Clean layout with student information cards.
- ⚡ **Dynamic Search:** Displays matching records based on the entered search keyword.
- 📡 **Internet Connectivity:** Uses Android internet permission to access remote services.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Android Studio | Development environment |
| Kotlin | Application programming language |
| XML | User interface design |
| HTTP GET | Fetching information from the web service |
| JSON | Data exchange format |
| Android SDK | Android application development |
| Android Emulator | Application testing |

## 🧠 Concepts Implemented

### 1. HTTP Requests

HTTP (Hypertext Transfer Protocol) allows an Android application to communicate with a remote server.

The application uses an HTTP GET request to retrieve student information from the web service.

### 2. JSON Parsing

JSON (JavaScript Object Notation) is a lightweight data-interchange format.

The web service returns student information in JSON format. The application reads the response, extracts the required fields, and displays them on the screen.

Example JSON structure:

```json
{
  "students": [
    {
      "id": 1,
      "name": "Rahul",
      "course": "MCA",
      "email": "rahul@gmail.com"
    },
    {
      "id": 2,
      "name": "Rashmi",
      "course": "MCA",
      "email": "rashmi@gmail.com"
    },
    {
      "id": 3,
      "name": "Priya",
      "course": "MBA",
      "email": "priya@gmail.com"
    },
    {
      "id": 4,
      "name": "Amit",
      "course": "BCA",
      "email": "amit@gmail.com"
    }
  ]
}
```

*Note: This illustrates the expected student data structure. The actual JSON response depends on the API configured in the application.*

### 3. Search Functionality

The search feature allows users to find student records by entering a student name or course.

The application compares the entered keyword with the available student records and displays the matching information.

### 4. Internet Permission

The Android application requires internet permission to communicate with the remote web service.

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## ⚙️ Application Workflow

1. Launch the Android application.
2. The application sends an HTTP GET request to the configured web service.
3. The server returns student information in JSON format.
4. The application parses the JSON response.
5. Student records are displayed as individual cards.
6. Enter a student name or course in the search field.
7. Click the **Search** button.
8. The application displays only the matching student records.

## 📸 Screenshots

### 1. Student Information – Initial Screen

The initial screen displays the available student records retrieved from the web service.

![Student Information Initial Screen](<img width="239" height="409" alt="student-list" src="https://github.com/user-attachments/assets/186d4724-8788-424e-bcec-b68d14a401b0" />
)

### 2. Student Search – Search by Name

When the user enters `Rashmi` and clicks the Search button, only the matching student record is displayed.

![Student Search Result](<img width="242" height="411" alt="search-rashmi" src="https://github.com/user-attachments/assets/13b37301-5175-438d-b23f-a1c1d679372d" />
)

## 🧪 Test Cases

| Test Case | Input | Expected Result |
|---|---|---|
| TC01 | Empty search field | Display all available students |
| TC02 | Rashmi | Display only Rashmi's details |
| TC03 | MCA | Display students enrolled in MCA |
| TC04 | MBA | Display students enrolled in MBA |
| TC05 | Unknown student name | Display no student found message |

## ▶️ How to Run the Project

1. Clone this repository:

2. Open Android Studio.

3. Select **Open** and choose the cloned project folder.

4. Wait for Gradle synchronization to complete.

5. Verify that the Android application has internet permission.

6. Ensure that the API URL in the application points to the correct online web service.

7. Start an Android emulator or connect an Android device.

8. Click the **Run** button.

9. Enter a student name or course in the search field and click **Search**.

## 📂 Project Structure

```text
Experiment-12/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/webservices/
│           │       └── MainActivity.kt
│           │
│           ├── res/
│           │   └── layout/
│           │       └── activity_main.xml
│           │
│           └── AndroidManifest.xml
│
├── screenshots/
│   ├── student-list.png
│   └── search-rashmi.png
│
├── build.gradle.kts
└── settings.gradle.kts
```

*The structure is illustrative; additional files may be present in the actual Android Studio project.*

## 📚 Learning Outcomes

After completing this experiment, the following concepts were understood:

- Understanding HTTP communication in Android.
- Accessing remote web services using HTTP requests.
- Retrieving and processing JSON data.
- Understanding JSON objects and arrays.
- Displaying server responses in an Android application.
- Implementing search functionality for retrieved records.
- Understanding internet permissions in Android.
- Developing an application that communicates with an external API.

## ✅ Result

Successfully developed an Android application that accesses web services using HTTP requests, retrieves student information in JSON format, and displays the records in a user-friendly interface.

The application also successfully implements student search functionality based on student names and course names.

---

## 👩‍💻 Developed By

**Name:** Rashmi Kumari  
**USN:** 25MCAR0222  
**Experiment:** 12  
**Subject:** Android Application Development
