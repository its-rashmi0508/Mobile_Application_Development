# Experiment 10: Implement Data Sharing Using Contacts Content Provider

## 📱 Experiment Details

- **Experiment Number:** 10
- **Experiment Name:** Implement Data Sharing Using Contacts Content Provider
- **Platform:** Android Studio
- **Programming Language:** Kotlin
- **UI Design:** XML
- **Package Name:** `com.example.contactprovider`

## 🎯 Aim

To develop an Android application that demonstrates data sharing using the Contacts Content Provider, allowing users to view, search, and share contact information through Android's sharing mechanism.

## 🧠 Objectives

- Understand the concept of Content Providers in Android.
- Access and manage contact information.
- Display contacts using a customized list interface.
- Implement contact searching by name or phone number.
- Share contact details using Android's Sharesheet.
- Understand how Android applications access and share data.

## 📚 Theory

### 1. Content Provider

A Content Provider is an Android component that manages and provides access to application data. It enables applications to access data through a standard interface.

### 2. Contacts Content Provider

The Contacts Content Provider provides access to contact information stored on an Android device. Android provides `ContactsContract` to define contact-related data and URIs.

### 3. ContentResolver

`ContentResolver` is used to query and access data exposed by a Content Provider. When reading protected device contacts, the application must obtain the appropriate permission.

### 4. Android Sharesheet

Android Sharesheet allows users to share text or other content with compatible applications. The Smart Contacts application uses a share button to open the sharing interface with the selected contact's details.

## ⚙️ Technologies Used

- Android Studio
- Kotlin
- XML
- Android SDK
- Contacts Content Provider
- `ContentResolver`
- RecyclerView or a suitable list adapter, depending on the implementation
- Android Intents and Sharesheet

## 📂 Project Structure

The following structure matches the files shown in Android Studio.

```text
ContactProvider/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── contactprovider/
│   │   │   │               ├── Contact.kt
│   │   │   │               ├── ContactAdapter.kt
│   │   │   │               ├── IndianContactsProvider.kt
│   │   │   │               └── MainActivity.kt
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   └── androidTest/
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
└── README.md
```

## 💻 Source Code

### 1. Contact.kt

This file represents the contact data model used to store contact information, such as a person's name and phone number.

```kotlin
data class Contact(
    val name: String,
    val phone: String
)
```

*Note: This is an illustrative snippet. Use the actual property names and types defined in your `Contact.kt` file.*

### 2. IndianContactsProvider.kt

This file contains the contact data access or provider logic used by the application.

Android's Contacts Content Provider can be queried using `ContentResolver` and `ContactsContract`.

```kotlin
val cursor = contentResolver.query(
    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
    null,
    null,
    null,
    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
)
```

The query returns contact data that can be read using a `Cursor`. Ensure the required contacts permission has been granted before accessing protected device contacts.

### 3. ContactAdapter.kt

This file connects the contact information to the list interface.

Its responsibilities include:

- Displaying contact names.
- Displaying phone numbers.
- Showing a share button for each contact.
- Handling user interactions with individual contact items.

### 4. MainActivity.kt

This file manages the main Smart Contacts screen and coordinates the application's user interactions.

The main features include:

- Displaying the contact list.
- Searching for contacts.
- Filtering contacts using the entered search query.
- Opening the Android Sharesheet when the share button is tapped.

### 5. Share Contact Using an Intent

Android's `Intent.ACTION_SEND` can be used to share contact information as text.

```kotlin
val sendIntent = Intent(Intent.ACTION_SEND).apply {
    type = "text/plain"
    putExtra(
        Intent.EXTRA_TEXT,
        "Contact Details:\n" +
            "Name: ${contact.name}\n" +
            "Phone: ${contact.phone}"
    )
}

startActivity(
    Intent.createChooser(sendIntent, "Share Contact")
)
```

This snippet illustrates the sharing mechanism. Adapt the variable names to match your actual implementation.

## ▶️ Steps to Execute

1. Open the `ContactProvider` project in Android Studio.
2. Allow Gradle synchronization to finish.
3. Open `MainActivity.kt` and verify the main screen and search functionality.
4. Open `Contact.kt` to review the contact data model.
5. Open `IndianContactsProvider.kt` to review how contact information is obtained.
6. Open `ContactAdapter.kt` to review how contacts are displayed.
7. Run the application on an Android emulator or device.
8. Verify that the contact list appears on the home screen.
9. Enter a contact name, such as `Deepa`, in the search field.
10. Verify that the matching contact is displayed.
11. Tap the share icon beside a contact.
12. Verify that the Android Sharesheet opens with the selected contact's details.
13. Select a compatible application or use the available copy option to share the information.

## 🧪 Sample Test Cases

| Test | Input / Action | Expected Output |
|---|---|---|
| 1 | Launch the application | Smart Contacts home screen appears |
| 2 | Enter `Deepa` in the search field | Matching contact Deepa Menon appears |
| 3 | Search for a phone number | Matching contact is displayed |
| 4 | Tap the share icon | Android Sharesheet opens |
| 5 | Select a sharing destination | Contact details are available for sharing |

## 📸 Screenshots

### 💜 Smart Contacts Home Screen

Displays the Smart Contacts interface, contact count, search field, contact names, phone numbers, and share buttons.

![Smart Contacts Home Screen](screenshots/HomeScreen_Contacts.png)

---

### 🔍 Search Contacts

Demonstrates searching for a contact by name. The application displays the matching contact based on the entered query.

![Search Contacts](screenshots/Search_contacts.png)

---

### 📇 Filtered Contact Output

Shows the contact list filtered to display a matching contact, including the contact name and phone number.

![Filtered Contact Output](screenshots/Filtered_contact.png)

---

### 📤 Share Contact Output

Displays the Android Sharesheet containing the selected contact's details, along with available sharing destinations.

![Share Contact Output](screenshots/Share_contact.png)

## 🧠 Concepts Learned

- Content Providers in Android
- `ContactsContract` and `ContentResolver`
- Kotlin data classes
- Displaying contact data using an adapter
- Searching and filtering contact information
- Sharing text using Android intents
- Android Sharesheet integration

## ✅ Result

The Smart Contacts Android application demonstrates contact listing, searching, and sharing functionality. Users can search for contacts by name or phone number and open the Android Sharesheet to share the selected contact's details.

## 📌 Conclusion

This experiment demonstrates how Android applications can access contact information through a Content Provider and share selected data with other applications. It provides practical experience with Android data access, list adapters, search functionality, intents, and the Sharesheet.
