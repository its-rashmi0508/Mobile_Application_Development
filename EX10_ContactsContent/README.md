# 📱 Experiment 10: Implement Data Sharing Using Contacts Content Provider

## 🎯 Aim

To implement data sharing in an Android application using the Contacts Content Provider to retrieve and display contact names and phone numbers stored on the device.

## 🧠 Objectives

- Understand the concept of Content Providers in Android.
- Access device contacts using `ContactsContract`.
- Request runtime permission to read contacts.
- Retrieve contact names and phone numbers using `ContentResolver`.
- Display the retrieved contact information in the application.

## 🛠️ Technologies Used

- **Language:** Kotlin
- **IDE:** Android Studio
- **Platform:** Android
- **UI Design:** XML
- **API:** Android Contacts Content Provider
- **Permission:** `READ_CONTACTS`

## 📂 Project Structure

```text
ContactProvider/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/contactprovider/
│           │       └── MainActivity.kt
│           ├── res/
│           │   └── layout/
│           │       └── activity_main.xml
│           └── AndroidManifest.xml
└── README.md
```

## ⚙️ Implementation

### 1. AndroidManifest.xml

The following permission is required to read contacts from the device.

```xml
<uses-permission
    android:name="android.permission.READ_CONTACTS" />
```

### 2. activity_main.xml

The user interface contains a **Load Contacts** button and a text area to display the retrieved contact information.

### 3. MainActivity.kt

The Kotlin activity performs the following operations:

- Checks whether contacts permission has been granted.
- Requests permission at runtime if necessary.
- Uses `ContentResolver` to query the Contacts Content Provider.
- Retrieves contact names and phone numbers.
- Displays the retrieved information in a `TextView`.

## ▶️ How to Run the Application

1. Clone or download this repository.
2. Open the project in Android Studio.
3. Wait for Gradle synchronization to complete.
4. Start an Android emulator or connect an Android device.
5. Ensure that the device contains sample contacts.
6. Run the application using the **Run ▶** button.
7. Tap **Load Contacts**.
8. Grant contacts permission when prompted.
9. Verify that the contact names and phone numbers appear on the screen.

## 🧪 Test Cases

| Test Case | Action | Expected Result |
|---|---|---|
| 1 | Grant contacts permission and tap Load Contacts | Contacts are displayed with names and phone numbers |
| 2 | Deny contacts permission | A permission-denied message is displayed |
| 3 | Use a device with no saved contacts | A message indicating that no contacts were found is displayed |

## 📸 Screenshots

### 🏠 Home Screen

The home screen displays the Load Contacts button before retrieving contact information.

![Home Screen](screenshots/home_screen.png)

---

### 🔐 Contacts Permission

Android requests permission before allowing the application to read device contacts.

![Contacts Permission](screenshots/permission_dialog.png)

---

### 📇 Contacts Output

The application displays the contact names and their corresponding phone numbers retrieved through the Contacts Content Provider.

![Contacts Output](screenshots/contacts_output.png)

---

## 🧠 Concepts Learned

- Content Providers in Android
- Contacts Content Provider and `ContactsContract`
- `ContentResolver` and `Cursor`
- Runtime permissions
- Reading and displaying contact information
- Data sharing between Android applications

## ✅ Result

Successfully implemented data sharing using the Contacts Content Provider in an Android application using Kotlin. The application requests permission, retrieves contact names and phone numbers, and displays the information stored on the device.

## 👩‍💻 Student Details

- **Name:** Rashmi Kumari
- **USN:** 25MCAR0222
- **Experiment:** 10
- **Title:** Implement Data Sharing Using Contacts Content Provider
