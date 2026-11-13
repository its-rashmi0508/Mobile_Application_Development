
# 💧 EcoTrack – Smart Water Consumption & Location Tracking

## 📌 Project Overview

**EcoTrack** is an Android application designed to help users monitor their daily water consumption and track the geographical locations where they consume beverages.

The application provides a simple and interactive platform to record water intake, set daily hydration goals, monitor consumption progress, and maintain a history of drinking activities along with their geographical locations.

EcoTrack integrates Android location services and Google Maps to automatically capture and display drinking locations whenever a consumption entry is recorded.

The application encourages healthy hydration habits by providing real-time consumption statistics, visual progress monitoring, and location-based tracking.

---

## 🎯 Project Objectives

The main objectives of EcoTrack are:

- To monitor daily water and beverage consumption.
- To allow users to record the amount of water or other beverages consumed.
- To set and manage personalized daily hydration goals.
- To automatically capture the user's geographical location whenever a drinking entry is added.
- To display daily consumption progress using a circular progress indicator.
- To maintain a history of previous consumption records.
- To display recorded drinking locations on Google Maps.
- To store user preferences and consumption records locally using SharedPreferences and SQLite.

---

## ✨ Key Features

### 1. 🏠 Home Dashboard

The Home screen provides an overview of the user's daily hydration activity.

Features include:
- Displays the total amount of beverages consumed during the day.
- Shows the daily hydration goal.
- Calculates the remaining amount required to achieve the goal.
- Displays the percentage of daily goal completion.
- Provides a circular progress indicator for visual monitoring.
- Includes quick navigation to other application sections.
- Allows users to edit their daily hydration goal.

### 2. 💧 Water Consumption Tracker

Users can record their daily beverage consumption.

Features include:
- Supports four beverage categories:
  - Water
  - Tea
  - Juice
  - Coffee
- Provides quick-add options:
  - 250 ml
  - 500 ml
  - 750 ml
- Allows users to enter a custom consumption amount.
- Automatically retrieves the current geographical location when available.
- Saves consumption details in the local SQLite database.
- Updates the dashboard after a successful entry.

### 3. 📍 Location Tracking

EcoTrack uses Android Location Services and the Fused Location Provider to capture the user's geographical location.

Features include:
- Automatically retrieves the current geographical coordinates.
- Captures latitude and longitude.
- Converts geographical coordinates into a readable location name using Geocoding.
- Associates the location with the corresponding water consumption record.
- Displays recorded drinking locations on Google Maps.
- Uses map markers to identify individual drinking locations.

### 4. 📊 Progress Monitoring

The application provides a visual representation of daily hydration progress.

Features include:
- Displays the total consumed amount.
- Displays the remaining amount.
- Calculates the percentage of the daily goal completed.
- Uses a circular progress indicator to represent progress.
- Updates automatically whenever a new consumption record is added or deleted.

### 5. 🕒 Consumption History

The History screen maintains previously recorded consumption details.

Each record displays:
- Beverage type.
- Amount consumed in millilitres.
- Geographical location.
- Date of consumption.
- Time of consumption.

Additional functionality:
- Displays previously saved consumption records.
- Allows users to delete individual records.
- Updates the database after deletion.
- Maintains a chronological record of drinking activities.

### 6. 🗺️ Google Maps Integration

EcoTrack integrates Google Maps to visualize drinking locations.

Features include:
- Displays a Google Map inside the application.
- Shows markers for recorded drinking locations.
- Displays the associated beverage type and quantity when a marker is selected.
- Allows users to explore the geographical locations where they recorded their consumption.

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Kotlin | Primary programming language |
| Android Studio | Android application development IDE |
| XML | Designing application layouts |
| Android SDK | Android application development framework |
| SharedPreferences | Storing daily hydration goals |
| SQLite | Local database for consumption history |
| Fused Location Provider | Retrieving current geographical location |
| Google Maps SDK | Displaying drinking locations |
| Geocoding | Converting coordinates into readable addresses |
| RecyclerView | Displaying consumption history |
| Material Design | Designing the application interface |
| Gradle Kotlin DSL | Project build configuration |

---

## 🏗️ Project Structure

```text
EcoTrack/
├── build.gradle.kts                          # Top-level Gradle build configuration
├── settings.gradle.kts                       # Gradle settings & module inclusion
├── gradle/
│   └── libs.versions.toml                    # Version Catalog for dependencies & plugins
├── app/
│   ├── build.gradle.kts                      # App module Gradle configuration & dependencies
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml           # App Manifest, Permissions & Google Maps API Key
│       │   ├── java/com/example/ecotrack/
│       │   │   ├── MainActivity.kt           # Main Controller (Navigation, GPS, Maps, Progress)
│       │   │   ├── WaterEntry.kt             # Data Model Class (Intake item, GPS, Address)
│       │   │   ├── GoalPreferences.kt        # SharedPreferences Manager for Daily Goal
│       │   │   ├── WaterDatabaseHelper.kt    # SQLite Helper for Database Persistence (DB v2)
│       │   │   └── HistoryAdapter.kt         # RecyclerView Adapter for Consumption History
│       │   └── res/
│       │       ├── drawable/
│       │       │   └── circular_progress_drawable.xml
│       │       │                               # Circular Arc Gauge Progress Drawable
│       │       ├── layout/
│       │       │   ├── activity_main.xml     # Multi-Tab Main Interface Layout
│       │       │   └── item_water_entry.xml  # Card Item Layout for History Log List
│       │       ├── menu/
│       │       │   └── bottom_nav_menu.xml   # Bottom Navigation Menu Items
│       │       └── values/
│       │           ├── colors.xml            # Color Palette Definition
│       │           ├── strings.xml           # String Resources
│       │           └── themes.xml            # Material3 App Theme Definition
│       ├── test/                             # Local Unit Tests
│       └── androidTest/                      # Instrumentation UI Tests
└── README.md                                 # Project Documentation
```

---

## 🗃️ Database Schema

EcoTrack uses **SQLite** for storing water consumption records locally on the device.

### `water_entries` Table

The `water_entries` table stores information about each beverage consumption entry, including the amount consumed, date, time, geographical coordinates, beverage category, and location name.

| Column Name | Data Type | Constraints | Description |
|:---|:---|:---|:---|
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | Unique Log ID |
| `amount` | `INTEGER` | `NOT NULL` | Volume consumed in ml |
| `timestamp` | `TEXT` | `NOT NULL` | Full Date & Time (`yyyy-MM-dd HH:mm:ss`) |
| `date` | `TEXT` | `NOT NULL` | Date string (`yyyy-MM-dd`) |
| `latitude` | `REAL` | `NOT NULL` | GPS Latitude Coordinate |
| `longitude` | `REAL` | `NOT NULL` | GPS Longitude Coordinate |
| `drink_type` | `TEXT` | `DEFAULT 'Water'` | Beverage Category (Water, Tea, Juice, Coffee) |
| `location_name` | `TEXT` | `DEFAULT 'Unknown'` | Geocoded City / Address Name |

### Database Operations

The application performs the following database operations:

- **INSERT:** Saves new beverage consumption records.
- **SELECT:** Retrieves previously saved consumption records.
- **UPDATE:** Updates records when supported by the application.
- **DELETE:** Removes individual consumption records.

SQLite provides persistent local storage, ensuring that consumption history remains available even after the application is closed and reopened.

---

## 💾 Data Persistence

EcoTrack uses two Android data persistence mechanisms.

### 1. SharedPreferences

SharedPreferences is used to store small amounts of data in key-value format.

In EcoTrack, it is used for:
- Storing the user's daily hydration goal.
- Retrieving the previously saved goal.
- Maintaining the selected goal when the application is restarted.

**Example:**

```text
daily_goal = 2000
```

### 2. SQLite Database

SQLite is used for storing structured consumption records.

It maintains:
- Consumption amount.
- Beverage category.
- Date and time.
- Latitude and longitude.
- Location name.

Unlike temporary variables, SQLite records remain stored locally after the application is closed.

---

## 📐 Progress Calculation

EcoTrack calculates daily hydration progress using the total consumption and the user's daily goal.

Let:

- **G** = Daily hydration goal (ml)
- **C** = Total amount consumed (ml)

### Remaining Amount

```text
Remaining = max(G - C, 0)
```

### Completion Percentage

```text
Completion (%) = (C / G) × 100
```

### Example

Suppose:

- Daily Goal = 2000 ml
- Consumed Amount = 1450 ml

Then:

```text
Remaining = 2000 - 1450
          = 550 ml
```

```text
Completion = (1450 / 2000) × 100
           = 72.5%
```

Therefore, the dashboard displays:

| Parameter | Value |
|---|---:|
| Daily Goal | 2000 ml |
| Consumed | 1450 ml |
| Remaining | 550 ml |
| Completed | 72.5% |

---

## 📍 Location Tracking Implementation

EcoTrack uses the **Fused Location Provider API** to retrieve the device's current location.

The location tracking process involves:

1. Requesting the required location permissions.
2. Checking whether device location services are enabled.
3. Requesting the current geographical location.
4. Retrieving latitude and longitude coordinates.
5. Converting coordinates into a readable address using Geocoding.
6. Associating the location details with the consumption entry.
7. Storing the information in SQLite.
8. Displaying the saved location on Google Maps.

### Required Permissions

The application requires the following permissions for location functionality:

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<uses-permission android:name="android.permission.INTERNET" />
```

Location permissions must also be requested at runtime on supported Android versions.

**Note:** Location tracking depends on device GPS availability, internet connectivity where needed, and the user's permission settings.

---

## 🗺️ Google Maps API Configuration

To enable Google Maps functionality:

1. Visit the [Google Cloud Console](https://console.cloud.google.com/).
2. Create a new Google Cloud project or select an existing project.
3. Enable the **Maps SDK for Android**.
4. Generate a Google Maps API key.
5. Configure the API key in the application's AndroidManifest.xml.
6. Restrict the API key using the Android package name and SHA-1 fingerprint.
7. Run the application on a device with internet access.

**Important:** Do not upload unrestricted API keys or private credentials to a public GitHub repository.

---

## 🚀 Installation and Setup

### Prerequisites

Before running EcoTrack, ensure that you have:

- Android Studio installed.
- Android SDK configured.
- Kotlin support enabled.
- A compatible Android emulator or physical Android device.
- Internet connectivity.
- A valid Google Maps API key.
- Location services enabled on the device.

### Step 1: Clone the Repository

Open the terminal and execute:

```bash
git clone https://github.com/your-username/EcoTrack.git
```

### Step 2: Open in Android Studio

1. Launch Android Studio.
2. Select **Open**.
3. Navigate to the downloaded EcoTrack project folder.
4. Open the project.
5. Wait for Gradle synchronization to complete.

### Step 3: Configure Google Maps

Add your Google Maps API key in the appropriate location in the AndroidManifest.xml file.

### Step 4: Run the Application

1. Connect your Android device using USB debugging or start an emulator.
2. Select the EcoTrack application.
3. Click the Run button.
4. Grant location permissions when prompted.
5. Start tracking your daily beverage consumption.

---

## 📱 Application Screenshots

### 1. Home Dashboard

The Home screen displays the daily consumption summary, hydration goal, remaining amount, and progress percentage.

<img width="720" height="1600" alt="home" src="https://github.com/user-attachments/assets/3dbef736-48c0-4058-bc5d-3fc805caabf7" />


### 2. Add Water / Record Consumption

Users can select a beverage category, choose a predefined quantity or enter a custom amount, and save the consumption entry with the available geographical location.

<img width="720" height="1600" alt="add-water" src="https://github.com/user-attachments/assets/549eb70a-7968-4381-b4ce-f561215ddd9a" />


### 3. Location Tracking – Google Maps

The Map screen displays recorded drinking locations using geographical markers. Selecting a marker displays associated consumption information.

<img width="720" height="1600" alt="map" src="https://github.com/user-attachments/assets/61c7249a-d6ec-4aec-8f6d-2d646409a56a" />


### 4. Consumption History

The History screen displays previously saved consumption records, including beverage type, amount, location, date, and time. Users can also delete individual entries.

<img width="720" height="1600" alt="history" src="https://github.com/user-attachments/assets/fcd4118a-54db-4a03-972b-6e08506fcf40" />


### 5. History – Delete Functionality

The application allows users to delete individual consumption records from the History screen. Once a record is deleted, the history list is automatically refreshed, and a confirmation message **"Entry deleted"** is displayed.

<img width="720" height="1600" alt="history-delete" src="https://github.com/user-attachments/assets/6eb711bf-c0b8-459b-ab89-80d6ff1c5bd4" />

---

## 🧪 Testing

The following scenarios can be used to test the application's main functionalities.

| Test Case | Expected Result |
|---|---|
| Open the application | Home dashboard should be displayed. |
| Add 250 ml of water | Consumption record should be saved successfully. |
| Select Tea or Coffee | Selected beverage category should be recorded. |
| Enter a custom amount | The entered quantity should be saved. |
| Change the daily goal | Updated goal should appear on the dashboard. |
| Add multiple consumption records | Daily consumption total should update. |
| Enable location permission | Application should retrieve the available location. |
| Open the Map screen | Saved locations with valid coordinates should appear as markers. |
| Open History | Previously saved records should be displayed. |
| Delete a history entry | Selected record should be removed from the database. |
| Restart the application | Previously saved goal and history should remain available. |

---

## 🔐 Privacy and Security

- Location permission is requested for recording drinking locations.
- The application respects the user's location permission choices.
- Consumption records are stored locally using SQLite.
- Daily goals are stored using SharedPreferences.
- Google Maps requires internet connectivity to load map content.
- API keys and sensitive configuration details should not be committed to a public repository.
- Location information should be handled responsibly.

---

## 🔮 Future Enhancements

Possible improvements for future versions include:

- Daily hydration reminder notifications.
- Weekly and monthly consumption reports.
- Graphical analytics for beverage consumption.
- Exporting consumption history into PDF or CSV.
- Customizable hydration goals.
- Cloud synchronization and backup.
- User authentication and personalized profiles.
- Improved location-based analytics.
- Dark mode support.

---

## 🎓 Android Concepts Demonstrated

This project demonstrates the practical implementation of several Android development concepts.

| Android Concept | Implementation in EcoTrack |
|---|---|
| Kotlin Programming | Application logic and functionality |
| XML Layouts | Designing application screens |
| SharedPreferences | Storing daily hydration goals |
| SQLite | Maintaining consumption history |
| RecyclerView | Displaying consumption records |
| ProgressBar / Drawable | Visualizing hydration progress |
| Location Services | Retrieving geographical coordinates |
| Fused Location Provider | Obtaining current device location |
| Google Maps API | Displaying drinking locations |
| Runtime Permissions | Requesting location access |
| Bottom Navigation | Switching between application sections |
| Material Design | Creating an interactive user interface |

---

## 🌱 Benefits of EcoTrack

- Encourages users to maintain healthy hydration habits.
- Makes daily beverage consumption easier to monitor.
- Helps users understand their hydration progress.
- Maintains a record of previous drinking activities.
- Provides geographical information about drinking locations.
- Demonstrates the integration of Android storage and location-based services.

---

## 👩‍💻 Author

**Name:** Rashmi Kumari

**Project:** EcoTrack – Smart Water Consumption & Location Tracking

**Platform:** Android

**Language:** Kotlin

**IDE:** Android Studio

---

## 📄 License

This project is developed for academic and educational purposes.

You may add an appropriate open-source license if you intend to distribute or allow others to reuse the project.

---

⭐ **If you find this project interesting, consider giving the repository a star!**
