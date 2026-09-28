# Experiment 7 – Adaptive UI Using ListView and ImageView

## 📱 Explore Wonders – Travel Destination Explorer

### 👩‍🎓 Student Details

| Field | Details |
|---|---|
| **Name** | Rashmi Kumari |
| **USN** | 25MCAR0222 |
| **Programme** | MCA |
| **Experiment** | Experiment 7 |
| **Platform** | Android |
| **Programming Language** | Kotlin |
| **UI Technology** | XML |
| **IDE** | Android Studio |
| **Application Scenario** | Travel Destination Explorer |

---

## 🎯 Aim

To create an **adaptive Android user interface using ListView and ImageView**, where users can browse a collection of travel destinations and view detailed information about a selected destination.

---

## 🎯 Objectives

The objectives of this experiment are:

- To understand adaptive user interface design in Android.
- To implement a `ListView` for displaying multiple items.
- To use `ImageView` for displaying destination images.
- To create and use a custom adapter.
- To handle item selection events.
- To display detailed information about a selected item.
- To understand XML-based Android UI design.
- To implement the application logic using Kotlin.
- To design an interface that works across different screen sizes.
- To understand single-pane and dual-pane adaptive layouts.

---

# 📖 Concept Behind the Experiment

## What is an Adaptive UI?

An **Adaptive User Interface (UI)** is a user interface that adjusts its layout and presentation according to the available screen size, orientation, and device configuration.

Android applications can run on different types of devices such as:

- Smartphones
- Tablets
- Foldable devices
- Portrait screens
- Landscape screens

Therefore, an application should not depend on a single fixed screen size.

An adaptive interface makes better use of the available space.

### Small Screen

On a smaller smartphone screen, the application can primarily display the destination list:

```text
--------------------------------
|       Explore Wonders        |
--------------------------------
| Alpine Summit Pass           |
--------------------------------
| Emerald Haven Lagoon         |
--------------------------------
| Forest Valley                |
--------------------------------
| Desert Landscape             |
--------------------------------
```

When the user selects a destination, its details can be displayed.

### Large Screen

On a larger screen, the application can display the list and details simultaneously:

```text
---------------------------------------------------
|                 Explore Wonders                 |
---------------------------------------------------
| Destination List |      Destination Details    |
|                  |                              |
| Alpine Summit    |      Destination Image      |
| Emerald Lagoon   |                              |
| Forest Valley    |      Alpine Summit Pass      |
| Desert Landscape |      Swiss Alps, Switzerland|
|                  |      ⭐ 4.9                   |
---------------------------------------------------
```

This is the main idea behind adaptive UI.

---

# 🧠 Technologies Used

## Kotlin

Kotlin is used as the primary programming language for implementing the application logic.

It is used for:

- Activity implementation
- Data handling
- ListView interaction
- Adapter implementation
- Click event handling
- Updating destination details
- Managing adaptive UI behavior

---

## XML

XML is used to design the Android user interface.

XML layouts define components such as:

- `ListView`
- `ImageView`
- `TextView`
- Buttons
- Layout containers
- Detail sections

Using XML separates the UI design from the Kotlin application logic.

---

## ListView

`ListView` is an Android UI component used to display a vertically scrollable list of items.

In this application, the ListView is used to display different travel destinations.

Example:

```text
Alpine Summit Pass
Emerald Haven Lagoon
Forest Valley
Desert Landscape
```

The user can scroll through the destinations and select an item.

---

## ImageView

`ImageView` is an Android UI component used to display images.

In this project, ImageView is used to display the image associated with the selected destination.

For example:

```text
        🏔️
Alpine Summit Pass
Swiss Alps, Switzerland
```

The image provides a visual representation of the selected destination.

---

## Custom Adapter

A custom adapter connects the destination data with the ListView.

The project contains:

```text
AestheticListAdapter
```

The adapter controls how each destination is displayed.

A list item can contain:

- Destination image
- Destination name
- Category
- Location
- Rating
- Other information

This makes the ListView more attractive and informative than a simple text list.

---

# 🌍 Application Scenario

## Explore Wonders – Travel Destination Explorer

The application demonstrates a **travel destination explorer** scenario.

The user can browse different natural wonders around the world.

The main screen contains:

> **Explore Wonders**

with the subtitle:

> **Curated natural wonders around the world**

The application organizes destinations into categories such as:

- All
- Mountains
- Oceans
- Forests
- Deserts

Users can browse the list and select a destination to view additional information.

---

# 🏔️ Example Destinations

## Alpine Summit Pass

**Category:** Mountain

**Location:** Swiss Alps, Switzerland

**Rating:** 4.9

The destination is displayed with an image, location, rating, and descriptive information.

---

## 🌊 Emerald Haven Lagoon

**Category:** Ocean

**Location:** Bora Bora, French Polynesia

**Rating:** 4.8

The destination is displayed as another item in the ListView.

---

# ✨ Application Features

### 1. Destination List

Multiple destinations are displayed using a scrollable `ListView`.

### 2. Destination Images

Each destination can contain an image displayed using `ImageView`.

### 3. Custom List Items

A custom adapter is used to create visually attractive destination items.

### 4. Destination Selection

The user can select a destination from the list.

### 5. Destination Details

The application displays additional information about the selected destination.

### 6. Rating

Destinations display ratings such as:

```text
⭐ 4.9
```

### 7. Location

The application displays the location of the destination.

Example:

```text
📍 Swiss Alps, Switzerland
```

### 8. Category

Destinations can belong to categories such as:

```text
MOUNTAIN
OCEAN
FOREST
DESERT
```

### 9. Adaptive Layout

The application can adjust the presentation according to the available screen size.

### 10. Detail Pane

On larger screens, the destination list and selected destination details can be displayed together.

---

# 📁 Project Structure

```text
List_View/
│
├── app/
│   │
│   └── src/
│       │
│       └── main/
│           │
│           ├── java/
│           │   └── com/
│           │       └── example/
│           │           └── list_view/
│           │               │
│           │               ├── MainActivity.kt
│           │               ├── AestheticListAdapter.kt
│           │               └── Destination.kt
│           │
│           ├── res/
│           │   ├── drawable/
│           │   ├── layout/
│           │   └── values/
│           │
│           └── AndroidManifest.xml
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
└── README.md
```

---

# 💻 Important Components

## MainActivity.kt

`MainActivity.kt` is the main Activity of the application.

It is responsible for:

- Initializing the UI.
- Finding the ListView.
- Creating the adapter.
- Handling destination selection.
- Updating the detail section.
- Managing the adaptive layout.

Important variables include:

```kotlin
private lateinit var listView: ListView
private lateinit var adapter: AestheticListAdapter
private var detailPane: View? = null
private var isDualPane: Boolean = false
```

The Activity acts as the main controller between the user interface and application logic.

---

# 🗂️ Destination.kt

The `Destination` class represents an individual destination.

A destination can contain information such as:

```text
Name
Category
Location
Rating
Description
Image
Reviews
```

A conceptual data class can be represented as:

```kotlin
data class Destination(
    val name: String,
    val category: String,
    val location: String,
    val rating: Double,
    val description: String,
    val imageResId: Int
)
```

This keeps destination information organized.

---

# 🎨 AestheticListAdapter.kt

`AestheticListAdapter` is the custom adapter used to connect destination data with the ListView.

It controls the appearance of each individual list item.

A list item can contain:

```text
ImageView
TextView – Destination Name
TextView – Category
TextView – Location
TextView – Rating
```

The adapter creates a consistent design for all destinations.

---

# 🔄 Application Workflow

The application works according to the following flow:

```text
                 Start Application
                         │
                         ▼
                MainActivity Loads
                         │
                         ▼
              Destination Data Created
                         │
                         ▼
                Custom Adapter Created
                         │
                         ▼
                      ListView
                         │
                         ▼
              Destinations Displayed
                         │
                         ▼
              User Selects Destination
                         │
                         ▼
               Selection Event Triggered
                         │
                         ▼
                Destination Details
                         │
                         ▼
                  ImageView Updated
```

---

# 📱 Adaptive UI Implementation

The project uses the concept of a detail pane to support adaptive layouts.

The application contains variables such as:

```kotlin
private var detailPane: View? = null
private var isDualPane: Boolean = false
```

These can be used to determine whether the application should display a single-pane or dual-pane interface.

---

# 📱 Single-Pane Layout

On a smaller device, the application can focus on the destination list or selected destination.

```text
--------------------------------
|       Explore Wonders        |
--------------------------------
| Alpine Summit Pass           |
--------------------------------
| Emerald Haven Lagoon         |
--------------------------------
| Forest Valley                |
--------------------------------
| Desert Landscape             |
--------------------------------
```

This prevents the screen from becoming overcrowded.

---

# 🖥️ Dual-Pane Layout

On a larger screen, the application can use the additional space to display both the list and details.

```text
------------------------------------------------
|              Explore Wonders                 |
------------------------------------------------
|                |                             |
| Destination    | Selected Destination        |
| List           |                             |
|                | Destination Image          |
| Alpine Summit  |                             |
| Emerald Lagoon | Alpine Summit Pass         |
| Forest Valley  | Swiss Alps, Switzerland     |
| Desert         | ⭐ 4.9                      |
|                |                             |
------------------------------------------------
```

This makes better use of larger displays.

---

# 🧩 Android Components Used

| Component | Purpose |
|---|---|
| `Activity` | Controls the application screen |
| `ListView` | Displays the destination list |
| `ImageView` | Displays destination images |
| `TextView` | Displays textual information |
| `Adapter` | Connects data with ListView |
| `View` | Represents UI elements |
| XML Layout | Defines the user interface |
| Kotlin | Implements application logic |
| Android Emulator | Used for testing |

---

# 🎨 UI Design Principles Demonstrated

## Consistency

All destination items follow a consistent visual structure.

## Visual Hierarchy

Important information such as the destination name and image is given visual importance.

## Readability

Destination information is separated into meaningful sections.

## Responsive Design

The UI can adapt to different screen sizes.

## User Interaction

Users can select destinations and view their details.

## Efficient Use of Space

The application can use additional screen space on larger devices.

---

# ⚙️ Software Requirements

The following software is required:

- Android Studio
- Android SDK
- Kotlin
- Gradle
- JDK compatible with the Android Studio project
- Android Emulator or physical Android device

---

# 💻 Hardware Requirements

Recommended hardware:

- Laptop/Desktop computer
- Minimum 8 GB RAM
- At least 10 GB free storage
- Android device or Android Emulator

---

# 🧪 Testing

The following test cases can be used to verify the application.

| Test Case | Action | Expected Result |
|---|---|---|
| TC01 | Launch the application | Explore Wonders screen appears |
| TC02 | Scroll through the list | Multiple destinations are displayed |
| TC03 | Select Alpine Summit Pass | Alpine Summit Pass details are displayed |
| TC04 | Select another destination | Details change to the selected destination |
| TC05 | Check destination image | Correct image is displayed |
| TC06 | Test on a small screen | UI remains usable |
| TC07 | Test on a larger screen | Available screen space is used effectively |
| TC08 | Change orientation | UI adapts according to the available layout |

---

# ✅ Expected Output

After launching the application, the user should see an interface similar to:

```text
              Explore Wonders
       Curated natural wonders around the world

  [All] [Mountains] [Oceans] [Forests] [Deserts]

  ┌──────────────────────────────────────┐
  │ 🏔 Alpine Summit Pass          ⭐4.9 │
  │    Swiss Alps, Switzerland           │
  └──────────────────────────────────────┘

  ┌──────────────────────────────────────┐
  │ 🌊 Emerald Haven Lagoon        ⭐4.8 │
  │    Bora Bora, French Polynesia       │
  └──────────────────────────────────────┘
```

Selecting a destination displays its detailed information and image.

---
# 📸 Screenshots

## 🏠 Home Screen

The main screen displays the **Explore Wonders** application with destination categories and a scrollable ListView.

![Explore Wonders Home Screen](screenshots/home_screen.png)

---

## 🏔️ Destination Details

When a destination is selected, the application displays its image, name, category, location, rating, and description.

![Destination Details](screenshots/destination_details.png)

---

## 🌊 Another Destination

The user can select another destination from the ListView and view its corresponding details.

![Emerald Haven Lagoon](screenshots/emerald_lagoon.png)

---

# 👍 Advantages

The application provides:

- Simple navigation
- Scrollable destination list
- Image-based destination identification
- Detailed destination information
- Custom list item design
- Adaptive screen layout
- Better utilization of larger displays
- Improved user experience

---

# 🚀 Future Enhancements

The application can be improved by adding the following features:

### 🔎 Search

Allow users to search for destinations.

### ❤️ Favorites

Allow users to save their favorite destinations.

### 🌐 API Integration

Connect the application to a travel or tourism API.

### 🗺️ Google Maps

Integrate maps to show the exact destination location.

### ☁️ Cloud Database

Store destination information using Firebase or another backend.

### 📱 Improved Tablet Support

Create specialized layouts for tablets and large-screen devices.

### 🔔 Notifications

Send notifications about destinations, travel information, or offers.

### ⭐ Reviews

Allow users to add ratings and reviews.

### 🌙 Dark Mode

Add support for light and dark themes.

---

# 🎓 Learning Outcomes

After completing this experiment, the student will be able to:

1. Explain the concept of adaptive UI in Android.
2. Create and configure a `ListView`.
3. Implement a custom adapter.
4. Use `ImageView` to display images.
5. Handle item click events.
6. Design XML-based Android interfaces.
7. Connect Kotlin application logic with XML views.
8. Create responsive layouts for different screen sizes.
9. Understand single-pane and dual-pane UI concepts.
10. Develop a basic real-world Android application.

---

# 🧠 Key Concepts Learned

```text
Android UI
    │
    ├── XML Layouts
    │
    ├── ListView
    │
    ├── ImageView
    │
    ├── TextView
    │
    ├── Custom Adapter
    │
    ├── Click Listeners
    │
    ├── Activity
    │
    └── Adaptive Layout
```

---

# 📌 Repository Information

| Item | Details |
|---|---|
| **Project Name** | List_View |
| **Experiment** | Experiment 7 |
| **Topic** | Adaptive UI using ListView and ImageView |
| **Language** | Kotlin |
| **UI Technology** | XML |
| **Scenario** | Explore Wonders – Travel Destination Explorer |
| **Student** | Rashmi Kumari |
| **USN** | 25MCAR0222 |
| **Programme** | MCA |

---

## 👩‍💻 Developed By

**Rashmi Kumari**  
**USN: 25MCAR0222**  
**MCA**
