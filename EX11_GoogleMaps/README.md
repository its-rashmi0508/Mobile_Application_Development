# 🗺️ OpenStreetMap Android Application

## 📱 Project Overview

This Android application demonstrates how to display an interactive map using **OpenStreetMap and MapLibre** in Android Studio. The application is developed using Kotlin and provides a map interface with navigation and map interaction features.

Unlike Google Maps SDK, this implementation does not require a Google Maps API key. It uses MapLibre to render a map from the OpenFreeMap Liberty style.

## 🎯 Aim

To develop an Android application that displays an interactive map using OpenStreetMap and provides basic map navigation features.

## 🧠 Objectives

- Display an interactive map in an Android application.
- Integrate OpenStreetMap-compatible map data using MapLibre.
- Implement map zooming and panning.
- Display a marker at a predefined location.
- Understand map integration using Kotlin.
- Load map styles from an online map provider.

## ⚙️ Technologies Used

- **Language:** Kotlin
- **IDE:** Android Studio
- **Map Library:** MapLibre Native for Android
- **Map Data:** OpenStreetMap-compatible map services
- **Map Style:** OpenFreeMap Liberty
- **Layout:** XML
- **Minimum SDK:** API 24

## ✨ Features

- 🗺️ Displays an interactive map.
- 📍 Displays a marker at India Gate, New Delhi.
- 🔍 Supports zooming through map gestures.
- 👆 Supports map panning and navigation.
- 🧭 Displays a compass when applicable.
- 🌐 Loads map styles over the internet.
- 🔑 Does not require a Google Maps API key.

## 📂 Project Structure

```text
MyGoogleMap/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/
│           │       └── example/
│           │           └── mygooglemap/
│           │               └── MainActivity.kt
│           ├── res/
│           │   ├── layout/
│           │   │   └── activity_main.xml
│           │   └── values/
│           └── AndroidManifest.xml
├── build.gradle.kts
└── README.md
```

## 📦 Dependencies

Add the following dependency to `build.gradle.kts` (Module: app):

```kotlin
implementation("org.maplibre.gl:android-sdk:13.6.1")
```

The project also uses AndroidX and Material Components for the application interface.

## 🧩 Implementation

### 1. Internet Permission

The application requires internet access to load online map tiles.

Add the following permission to `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### 2. Initialize MapLibre

MapLibre is initialized in `MainActivity.kt`:

```kotlin
MapLibre.getInstance(this)

mapView = findViewById(R.id.mapView)
mapView.onCreate(savedInstanceState)
```

### 3. Load the Map Style

The application uses the OpenFreeMap Liberty style:

```kotlin
private val normalStyle =
    "https://tiles.openfreemap.org/styles/liberty"
```

Load the style using:

```kotlin
mapLibreMap.setStyle(normalStyle)
```

### 4. Set the Default Location

The map is initially centered on India Gate, New Delhi.

```kotlin
val defaultLocation = LatLng(28.6129, 77.2295)

mapLibreMap.cameraPosition = CameraPosition.Builder()
    .target(defaultLocation)
    .zoom(12.0)
    .build()
```

### 5. Add a Map Marker

A marker identifies the default location.

```kotlin
mapLibreMap.addMarker(
    MarkerOptions()
        .position(defaultLocation)
        .title("India Gate")
        .snippet("New Delhi, India")
)
```

### 6. Enable Map Interaction

Map gestures allow users to explore the map.

```kotlin
mapLibreMap.uiSettings.isZoomGesturesEnabled = true
mapLibreMap.uiSettings.isCompassEnabled = true
```

Users can zoom in and out using pinch gestures and move around the map by dragging it.

## ▶️ How to Run the Project

1. Install Android Studio.
2. Clone or download this repository.
3. Open the project in Android Studio.
4. Allow Gradle to synchronize the project dependencies.
5. Ensure the device or emulator has internet access.
6. Connect an Android device or start an emulator.
7. Click **Run ▶** to launch the application.
8. Wait for the online map style to load.

## 🧪 Test Cases

| Test Case | Action | Expected Result |
|---|---|---|
| TC01 | Launch the application | Map screen opens |
| TC02 | Load the online map style | Map tiles appear when the service is available |
| TC03 | Zoom in using pinch gestures | Map zoom level increases |
| TC04 | Zoom out using pinch gestures | Map zoom level decreases |
| TC05 | Drag the map | Map moves in the selected direction |
| TC06 | View the default location | Map opens centered on New Delhi |
| TC07 | Check the marker | India Gate marker is displayed |

## 📸 Screenshots

Add your actual application screenshots to a `screenshots` folder in the repository.

### 1. Application Interface

Shows the main map screen.

![Application Interface](screenshots/map_home.png)

### 2. Map with Marker

Shows the map centered on India Gate, New Delhi.

![Map with Marker](screenshots/map_marker.png)

### 3. Zoomed Map

Shows the map after zooming in.

![Zoomed Map](screenshots/map_zoom.png)

### 4. Map Navigation

Shows the map after moving to a different location.

![Map Navigation](screenshots/map_navigation.png)

**Note:** Replace these image paths with your actual screenshot filenames. The images will appear in GitHub after you upload the corresponding files.

## 📋 Result

The Android application demonstrates the integration of MapLibre with an OpenStreetMap-compatible map service. It supports map display, navigation gestures, zooming, and a marker at a predefined location without requiring a Google Maps API key.

## ⚠️ Notes

- An internet connection is required to load the online map style and tiles.
- Map display depends on the availability of the configured map service.
- The current implementation uses the Liberty style; satellite, terrain, and hybrid views require additional map-style providers.
- Current-location detection requires additional implementation and runtime location permissions.
- The application must follow the selected map provider's attribution and usage policies.

## 👩‍💻 Author

**Rashmi Kumari**

## 📄 License

This project was developed for educational and academic purposes. Review the licenses and usage policies of the libraries, map data, and map services used in the application.
