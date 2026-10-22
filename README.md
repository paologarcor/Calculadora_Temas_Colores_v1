# Calculadora

Calculadora is a native Android application developed in Kotlin. It provides a clean, modern user interface with support for multiple color themes, standard arithmetic calculations, decimal formatting, and edge-to-edge display layouts.

## Features

- **Standard Arithmetic Operations**: Addition, subtraction, multiplication, and division.
- **Advanced Options**: Percentage calculations, sign toggling (+/-), and AC (All Clear) reset functionality.
- **Dynamic Themes**: Switch between three color themes (Lavender, Mint, and Peach) using an interactive popup menu.
- **Modern UI**: Built using ConstraintLayout, Material Components, and custom card shapes with edge-to-edge support for modern Android devices.
- **Precise Decimal Formatting**: Formats calculation results cleanly up to 8 decimal places.

## Tech Stack

- **Language**: Kotlin
- **UI Toolkit**: Android Views (ConstraintLayout, MaterialCardView, TextView, Button)
- **Architecture**: Activity-based controller pattern (`MainActivity`)
- **Jetpack Libraries**: 
  - `androidx.appcompat`
  - `androidx.core:core-ktx`
  - `androidx.activity:activity-edge-to-edge`
  - Material Components for Android (`com.google.android.material`)

## Project Structure

```text
Calculadora/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/example/calculadora/
│           │   └── MainActivity.kt
│           └── res/
│               ├── drawable/
│               ├── layout/
│               │   └── activity_main.xml
│               └── values/
│                   ├── colors.xml
│                   ├── strings.xml
│                   └── themes.xml
└── build.gradle.kts
```

## Getting Started

### Prerequisites

- Android Studio (Koala or newer recommended)
- Android SDK (API level 24 or higher)
- An Android Emulator or physical Android test device with USB debugging enabled.

### Installation and Execution

1. Clone or open this repository in Android Studio.
2. Ensure Gradle synchronization completes successfully.
3. Connect a physical Android device or start an Android Emulator (AVD).
4. Click the **Run** button in Android Studio to build and deploy the app.

## License

This project is open-source and available for educational and personal use.
