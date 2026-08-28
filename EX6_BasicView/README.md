# Experiment 6 – Develop an Android Application Using Basic Views

## 📱 Basic Views Android Application

### Experiment Objective

To develop an Android application using basic Android Views and understand the implementation and functionality of commonly used user interface components such as **TextView, EditText, Button, ImageView, CheckBox, RadioButton, Switch, and Toast**.

---

## 📖 Overview

This experiment demonstrates the development of an Android application using **basic Android Views**. The application provides a simple and interactive **Student Information Form** where users can enter their details and interact with different UI components.

The application logic is implemented using **Kotlin**, while the user interface is designed using **XML**.

The application demonstrates how multiple Android Views can work together to create an interactive form-based application.

---

## 🎯 Objective

The objectives of this experiment are:

* To understand the concept of Android Views.
* To design a user interface using XML.
* To collect user input using `EditText`.
* To perform actions using `Button`.
* To select a single option using `RadioButton`.
* To select multiple options using `CheckBox`.
* To implement ON/OFF functionality using `Switch`.
* To display feedback using `Toast`.
* To handle user interactions using Kotlin.

---

# 📚 Concepts and Technologies Used

## 1. Basic Android Views

Android Views are the fundamental building blocks used to create the user interface of an Android application.

### TextView

`TextView` is used to display text to the user.

Example:

```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Student Information Form"
    android:textSize="26sp" />
```

### EditText

`EditText` allows users to enter information.

Example:

```xml
<EditText
    android:id="@+id/etName"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Enter name" />
```

### Button

`Button` is used to perform an action when clicked.

Example:

```xml
<Button
    android:id="@+id/btnSubmit"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Submit" />
```

### RadioButton

`RadioButton` allows the user to select one option from a group.

Example:

```xml
<RadioGroup
    android:id="@+id/radioGroup"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <RadioButton
        android:id="@+id/rbMale"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Male" />

    <RadioButton
        android:id="@+id/rbFemale"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Female" />

</RadioGroup>
```

### CheckBox

`CheckBox` allows users to select multiple options.

Example:

```xml
<CheckBox
    android:id="@+id/cbCoding"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Coding" />
```

### Switch

`Switch` provides ON/OFF functionality.

Example:

```xml
<Switch
    android:id="@+id/switchNotifications"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Enable Notifications" />
```

### Toast

`Toast` displays a short feedback message to the user.

Example:

```kotlin
Toast.makeText(
    this,
    "Form Submitted Successfully!",
    Toast.LENGTH_SHORT
).show()
```

---

# 💡 Scenario Used for Demonstration

The application implements a **Student Information and Preferences Form**.

The user can:

1. Enter their **Name**.
2. Enter their **USN**.
3. Select their **Gender** using RadioButtons.
4. Select one or more interests using CheckBoxes.
5. Enable or disable notifications using a Switch.
6. Click the **Submit** button.
7. Receive a confirmation message using Toast.

This scenario demonstrates how different basic Android Views can be combined to create a practical form-based application.

---

# 🛠️ Technologies Used

| Technology         | Purpose                                                    |
| ------------------ | ---------------------------------------------------------- |
| **Android Studio** | Integrated Development Environment for Android development |
| **Kotlin**         | Programming language used for application logic            |
| **XML**            | Used to design the user interface                          |
| **Android SDK**    | Provides Android development APIs and tools                |
| **Gradle**         | Build automation and dependency management                 |

---

# 📂 Project Folder Structure

```text
Experiment-6-Basic-Views/
│
├── app/
│   ├── manifests/
│   │   └── AndroidManifest.xml
│   │
│   ├── java/
│   │   └── com.example.basicviewsapp/
│   │       └── MainActivity.kt
│   │
│   ├── res/
│   │   ├── drawable/
│   │   │   └── (Drawable resources)
│   │   │
│   │   ├── mipmap/
│   │   │   └── (Application icons)
│   │   │
│   │   ├── layout/
│   │   │   └── activity_main.xml
│   │   │
│   │   └── values/
│   │       ├── colors.xml
│   │       ├── strings.xml
│   │       └── themes.xml
│   │
│   └── build.gradle.kts
│
├── screenshots/
│   ├── output.png
│   ├── test_case_1.png
│   ├── test_case_2.png
│   └── test_case_3.png
│
├── build.gradle.kts
└── README.md
```

---

# ⚙️ How the Application Works

1. The user opens the application.
2. The **Student Information Form** is displayed.
3. The user enters their Name and USN.
4. The user selects a Gender using RadioButtons.
5. The user selects one or more interests using CheckBoxes.
6. The user enables or disables notifications using the Switch.
7. The user clicks the **Submit** button.
8. Kotlin retrieves the entered information from the Views.
9. The application validates the Name and USN fields.
10. If the fields are empty, an appropriate Toast message is displayed.
11. If valid information is entered, the application displays a successful submission message.

---

# 🖥️ User Interface Implementation

The layout is created in:

```text
app → res → layout → activity_main.xml
```

The complete XML code is given below.

## `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="24dp">

        <!-- Title -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Student Information Form"
            android:textSize="26sp"
            android:textStyle="bold"
            android:layout_gravity="center"
            android:layout_marginBottom="24dp" />

        <!-- Name -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Enter Your Name"
            android:textSize="16sp" />

        <EditText
            android:id="@+id/etName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Enter name"
            android:inputType="textPersonName" />

        <!-- USN -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:text="Enter Your USN"
            android:textSize="16sp" />

        <EditText
            android:id="@+id/etUsn"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Enter USN"
            android:inputType="text" />

        <!-- Gender -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:text="Select Gender"
            android:textStyle="bold" />

        <RadioGroup
            android:id="@+id/radioGroup"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <RadioButton
                android:id="@+id/rbMale"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Male" />

            <RadioButton
                android:id="@+id/rbFemale"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Female" />

            <RadioButton
                android:id="@+id/rbOther"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Other" />

        </RadioGroup>

        <!-- Interests -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:text="Select Your Interests"
            android:textStyle="bold" />

        <CheckBox
            android:id="@+id/cbCoding"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Coding" />

        <CheckBox
            android:id="@+id/cbSports"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Sports" />

        <CheckBox
            android:id="@+id/cbMusic"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Music" />

        <!-- Switch -->
        <Switch
            android:id="@+id/switchNotifications"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:text="Enable Notifications" />

        <!-- Submit Button -->
        <Button
            android:id="@+id/btnSubmit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="24dp"
            android:text="Submit" />

    </LinearLayout>

</ScrollView>
```

---

# 💻 Kotlin Implementation

The application logic is implemented in:

```text
app → java → com.example.basicviewsapp → MainActivity.kt
```

## `MainActivity.kt`

```kotlin
package com.example.basicviewsapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connect XML Views
        val etName = findViewById<EditText>(R.id.etName)
        val etUsn = findViewById<EditText>(R.id.etUsn)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        val cbCoding = findViewById<CheckBox>(R.id.cbCoding)
        val cbSports = findViewById<CheckBox>(R.id.cbSports)
        val cbMusic = findViewById<CheckBox>(R.id.cbMusic)

        val switchNotifications =
            findViewById<Switch>(R.id.switchNotifications)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        // Submit Button Click
        btnSubmit.setOnClickListener {

            val name = etName.text.toString().trim()
            val usn = etUsn.text.toString().trim()

            // Validation
            if (name.isEmpty() || usn.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter Name and USN",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Get selected gender
            val selectedGenderId = radioGroup.checkedRadioButtonId

            val gender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else {
                "Not Selected"
            }

            // Get selected interests
            val interests = mutableListOf<String>()

            if (cbCoding.isChecked) interests.add("Coding")
            if (cbSports.isChecked) interests.add("Sports")
            if (cbMusic.isChecked) interests.add("Music")

            // Notification status
            val notificationStatus =
                if (switchNotifications.isChecked) "Enabled" else "Disabled"

            // Display result
            val message = """
                Name: $name
                USN: $usn
                Gender: $gender
                Interests: ${interests.joinToString(", ")}
                Notifications: $notificationStatus
            """.trimIndent()

            Toast.makeText(
                this,
                "Form Submitted Successfully!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
```

---

# 🔍 Code Explanation

### Connecting XML Views

`findViewById()` is used to connect the Views defined in XML with Kotlin.

```kotlin
val etName = findViewById<EditText>(R.id.etName)
```

This allows Kotlin code to access and manipulate the corresponding `EditText`.

### Reading User Input

The entered text is retrieved using:

```kotlin
val name = etName.text.toString().trim()
val usn = etUsn.text.toString().trim()
```

### Input Validation

The application checks whether Name or USN is empty:

```kotlin
if (name.isEmpty() || usn.isEmpty()) {
    Toast.makeText(
        this,
        "Please enter Name and USN",
        Toast.LENGTH_SHORT
    ).show()
}
```

### Getting RadioButton Selection

The selected RadioButton is obtained using:

```kotlin
val selectedGenderId = radioGroup.checkedRadioButtonId
```

The selected gender is then retrieved from the selected RadioButton.

### Getting CheckBox Values

The application checks each CheckBox:

```kotlin
if (cbCoding.isChecked) interests.add("Coding")
if (cbSports.isChecked) interests.add("Sports")
if (cbMusic.isChecked) interests.add("Music")
```

Multiple interests can therefore be selected.

### Checking Switch Status

The Switch state is checked using:

```kotlin
if (switchNotifications.isChecked)
```

The application identifies whether notifications are **Enabled** or **Disabled**.

### Displaying Toast

Finally, the application provides feedback using:

```kotlin
Toast.makeText(
    this,
    "Form Submitted Successfully!",
    Toast.LENGTH_SHORT
).show()
```

---

# 🖼️ Application Output

The main application screen contains:

* Student Information Form title
* Name input field
* USN input field
* Gender RadioButtons
* Interest CheckBoxes
* Notification Switch
* Submit Button

Add the actual screenshot to:

```text
screenshots/output.png
```

Then reference it in GitHub using:

```markdown
![Application Output](screenshots/output.png)
```

---

# 🧪 Test Cases

## Test Case 1 – Valid Student Information

### Description

This test verifies whether the application correctly accepts valid student information and user selections.

### Input

* **Name:** Rashmi Kumari
* **USN:** 25MCAR0222
* **Gender:** Female
* **Interests:** Coding, Music
* **Notifications:** Enabled
* Click the **Submit** button.

### Expected Result

The application should accept the entered information and display:

```text
Form Submitted Successfully!
```

### Screenshot

Store the screenshot as:

```text
screenshots/test_case_1.png
```

Reference it using:

```markdown
![Test Case 1](screenshots/test_case_1.png)
```

---

## Test Case 2 – Empty Input Validation

### Description

This test verifies the application's behavior when required input fields are empty.

### Input

* Leave Name empty.
* Leave USN empty.
* Click the **Submit** button.

### Expected Result

The application should display:

```text
Please enter Name and USN
```

### Screenshot

Store the screenshot as:

```text
screenshots/test_case_2.png
```

Reference it using:

```markdown
![Test Case 2](screenshots/test_case_2.png)
```

---

## Test Case 3 – Multiple View Interaction

### Description

This test demonstrates the interaction of multiple basic Android Views while clearly displaying the student's **Name and USN**.

### Input

* **Name:** Rashmi Kumari
* **USN:** 25MCAR0222
* **Gender:** Female
* **Interests:** Coding, Sports, Music
* **Notifications:** Enabled
* Click the **Submit** button.

### Expected Result

The application should successfully process the entered information and display a confirmation message.

The test case should clearly demonstrate the use of:

* EditText
* RadioButton
* CheckBox
* Switch
* Button
* Toast

### Screenshot

Store the screenshot as:

```text
screenshots/test_case_3.png
```

Reference it using:

```markdown
![Test Case 3 – Name and USN](screenshots/test_case_3.png)
```


---

Using a separate screenshots folder keeps the GitHub repository organized and makes it easier to reference application outputs and test cases.

---

# 📖 Learning Outcomes

After completing this experiment, the following concepts were learned:

* Understanding Android Views.
* Designing an Android UI using XML.
* Using `TextView` to display information.
* Using `EditText` to collect user input.
* Using `Button` to perform actions.
* Using `RadioButton` for single selection.
* Using `CheckBox` for multiple selections.
* Using `Switch` for ON/OFF functionality.
* Handling user interactions using Kotlin.
* Performing basic input validation.
* Displaying feedback using Toast messages.
* Organizing an Android project for GitHub.

---

# 🚀 Conclusion

This experiment successfully demonstrates the development of an Android application using basic Android Views.

The **Student Information and Preferences Form** combines different UI components such as TextView, EditText, RadioButton, CheckBox, Switch, and Button into a single interactive application.

The experiment provides practical knowledge of:

* XML-based UI development
* Kotlin programming
* Event handling
* User input collection
* Input validation
* Android View interaction
* Toast notifications

These concepts form the foundation for developing more advanced Android applications such as registration forms, feedback forms, login screens, profile forms, and preference-based applications.

---

# 👩‍💻 Student Details

| Detail            | Information                                                     |
| ----------------- | --------------------------------------------------------------- |
| **Name**          | Rashmi Kumari                                                   |
| **USN**           | 25MCAR0222                                                      |
| **Experiment**    | Experiment 6 – Develop an Android Application Using Basic Views |
| **Platform**      | Android                                                         |
| **Language**      | Kotlin                                                          |
| **UI Technology** | XML                                                             |
| **IDE**           | Android Studio                                                  |

---

# ⭐ GitHub Repository

This project can be uploaded to GitHub with the following structure:

Experiment-6-Basic-Views/
│
├── app/
├── screenshots/
│   ├── output.png
│   ├── test_case_1.png
│   ├── test_case_2.png
│   └── test_case_3.png
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md

The repository contains the complete Android application, source code, screenshots, test cases, and documentation.

---
