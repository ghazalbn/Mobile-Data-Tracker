# Mobile-Data-Tracker App

The Data Tracker app is an Android application that allows users to monitor and track their mobile network data usage. It provides insights into their data usage patterns and enables them to set usage limits to manage their data consumption effectively.

## Table of Contents
- [Project Description](#project-description)
- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Usage](#usage)
- [Libraries and Dependencies](#libraries-and-dependencies)
- [Permissions](#permissions)
- [Implementation](#implementation)
    - [Project Structure](#project-structure)
    - [Important Files and Components](#important-files-and-components)
- [Contributing](#contributing)
- [License](#license)
- [Credits](#credits)

## Project Description

The goal of this project is to develop an Android app that empowers users to have a comprehensive understanding of their data usage habits and make informed decisions about their mobile data consumption. The app will offer the following features:

1. **Current Data Usage**: Users can connect to their mobile network and view their real-time data usage. The app will capture data usage information from the network provider and present it in a user-friendly format.

2. **Data Usage Patterns**: The app will provide graphical representations of users' data usage patterns over time. This feature allows users to identify trends, peak usage periods, and potential areas for data optimization.

3. **Usage Limits**: Users can set their desired data usage limits within the app. The application will notify users when they are approaching or exceeding their predefined limits, helping them stay within their data plan constraints.


## Features

- Usage tracking: Monitor and track your daily and monthly mobile data usage.
- Set data usage limit: Set a limit for your daily mobile data usage.
- Notifications: Receive notifications when you reach your set data usage limit.
- Data usage history: View your data usage history for the past 30 days.
- Graphical representation: Visualize your data usage with charts and graphs.
- Analyze data consumation: compare the daily data consumption with the average data consumed in the last 30 days, providing users with insights into their usage habits.

## Screenshots

<div style="display:flex; justify-content:center;">
  <img src="https://github.com/ghazalbn/Mobile-Data-Tracker/assets/56255992/586bfd7f-9ece-4783-be17-0056b6f7b7d9" alt="Screenshot 1" width="250" style="margin-right: 200px;"/>
  <img src="https://github.com/ghazalbn/Mobile-Data-Tracker/assets/56255992/e893b920-f56b-4df1-90fb-0998da1ef465" alt="Screenshot 2" width="250" style="margin-right: 200px;"/>
  <img src="https://github.com/ghazalbn/Mobile-Data-Tracker/assets/56255992/88d0f565-a9bf-4499-aead-8f3ac9f29481" alt="Screenshot 3" width="250"/>
</div>
<div style="display:flex; justify-content:center;" margin-top: 100px;">  
  <img src="https://github.com/ghazalbn/Mobile-Data-Tracker/assets/56255992/764b608a-f898-4e5d-b972-ab4b1b89c739" alt="Screenshot 4" width="250" style="margin-right: 200px;"/>
  <img src="https://github.com/ghazalbn/Mobile-Data-Tracker/assets/56255992/fb3aa4f4-1ddb-4c26-888b-b54d1d1a8841" alt="Screenshot 5" width="250" style="margin-right: 200px;"/>
  <img src="https://github.com/ghazalbn/Mobile-Data-Tracker/assets/56255992/0765dac5-ce29-4ef8-b44a-27f656b58dc1" alt="Screenshot 6" width="250"/>
</div>

## Installation

To use the Data Tracker app, follow these steps:

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync the project with the Gradle files.
4. Build and run the app on an Android device or emulator.

## Usage

Upon launching the app, you will see the main screen with the following options:

- **Data Usages**: This section displays your current daily data usage, including upload and download speeds.
- **Monthly Data Usages**: This section shows a list of your data usage history for the past 30 days.
- **Analize Data Usage**: Tap on the exclamation mark next to today's data usage, to see your data analysis for the day.
- **Set Limit**: Tap the upper left button to set your data usage limit for the day.
- **Graph**: Tap the upper right button to view a graphical representation of your data usage.

## Libraries and Dependencies

The Data Tracker app utilizes the following libraries:

- `dev.jahidhasanco.networkusage`: A library for tracking network usage statistics.
- `com.github.PhilJay:MPAndroidChart`: A powerful charting library for Android.

The Data Tracker app relies on the following dependencies:

- `androidx.core:core-ktx:1.8.0`
- `org.jetbrains.kotlin:kotlin-bom:1.8.0`
- `androidx.lifecycle:lifecycle-runtime-ktx:2.3.1`
- `androidx.appcompat:appcompat:1.6.1`
- `androidx.constraintlayout:constraintlayout:2.1.4`
- `junit:junit:4.13.2`
- `androidx.test.ext:junit:1.1.5`
- `androidx.test.espresso:espresso-core:3.5.1`
- `com.github.JahidHasanCO:NetworkUsage:1.0.1`
- `com.google.android.material:material:1.9`

## Permissions

The Data Tracker app requires the following permissions:

- `android.permission.POST_NOTIFICATIONS`: Allows the app to post notifications.
- `android.permission.READ_PHONE_STATE`: Grants access to read phone state information.
- `android.permission.PACKAGE_USAGE_STATS`: Requests access to package usage statistics.
- `android.permission.READ_PRIVILEGED_PHONE_STATE`: Provides privileged access to read phone state information.
- `android.permission.ACCESS_NETWORK_STATE`: Allows the app to access information about network connectivity.
- `android.permission.FOREGROUND_SERVICE`: Enables the app to run foreground services.
- `android.permission.RECEIVE_BOOT_COMPLETED`: Grants the app the ability to receive the BOOT_COMPLETED broadcast after the device boots up.

## Implementation

The Data Tracker app is implemented using Kotlin and follows the Model-View-ViewModel (MVVM) architecture. It utilizes various Android components and libraries, including:

- Android Architecture Components: LiveData, ViewModel, and Room Database.
- Android Data Binding: Used for binding UI components to data.
- RecyclerView: Used for displaying the list of monthly data usages.
- MPAndroidChart: Used for displaying the bar chart representing the data usage for the past 7 days.
- SharedPreferences: Used for storing user preferences, such as the data usage limit and notification settings.

The app makes use of a `NetworkMonitorService` to monitor internet usage in the background and a `ProfilePreferences` object to store and retrieve user preferences.

### Implementation Steps

1. **Android Studio and SDK**: The app was built using Android Studio, utilizing the Android Software Development Kit (SDK). This development environment provided the necessary tools and resources for creating Android applications.

2. **Data Capture**: The app connected to the mobile network and captured data usage information from the network provider. This involved leveraging relevant APIs provided by the network provider to retrieve accurate and up-to-date data usage details.

3. **Data Analysis**: Once the data usage information was captured, the app analyzed the data to generate insights into usage patterns. This involved applying data analytics techniques to identify trends, peak usage periods, and other relevant metrics. The insights were presented to the user in a graphical format for better visualization.

4. **Usage Limit Management**: Users had the ability to set their desired data usage limits within the app. The app provided a user-friendly interface to define the limit and configured notifications to alert users when they approached or exceeded the specified threshold.

### Project Structure

The Data Tracker project follows the standard Android project structure. The main components and files include:

- `app/src/main/java/com/firstapp/datatracker/`: Contains the Java/Kotlin source files.
- `app/src/main/res/`: Contains the resources such as layouts, drawables, and strings.
- `app/src/main/AndroidManifest.xml`: Specifies the app's package name, permissions, and activities.
- `app/build.gradle`: Includes the app-level dependencies and configuration.

### Important Files and Components

The project consists of the following key files:

- **DataModel.kt**: Create the DataModel data class to represent the data structure for storing mobile data usage information. This class will have properties for mobile data usage and date.

- **MainActivity.kt**: This file contains the implementation of the main screen of the app. It handles the UI interactions and displays the various sections, including daily data usage, monthly data usages, and buttons for setting the limit and viewing the graph.

- **DataAdapter.kt**: Implement the DataAdapter class, which extends RecyclerView.Adapter, to bind the data from the DataModel to the UI elements in the RecyclerView. This adapter will inflate the item layout and bind the data to the corresponding views.

- **SetLimitActivity.kt**: This file implements the activity for setting the data usage limit. It provides an input field for users to enter their desired limit and handles the submission of the limit.

- **NetworkMonitorService.kt**: This file includes the implementation of a background service that monitors internet usage. It periodically checks the data usage and sends notifications if the limit is exceeded.

- **ProfilePreferences.kt**: This file defines a shared preferences object for storing user preferences, such as the data usage limit and notification settings.

- **ChartActivity.kt**: Implements the ChartActivity class, which displays a bar chart representing the mobile data usage for the last 30 days. It uses the NetworkUsageManager to retrieve the usage data, prepares the data for the chart, and uses the MPAndroidChart library to create and customize the bar chart.

- **activity_chart.xml**: This XML layout file defines the layout for the activity that displays the graphical representation of data usage.

- **activity_main.xml**: This XML layout file defines the layout for the main screen of the app, including the sections for daily data usage and monthly data usages.

- **activity_set_limit.xml**: This XML layout file defines the layout for the activity that allows users to set the data usage limit.

- **item_data_usage.xml**: This XML layout file defines the layout for each item in the list of monthly data usages.


## Contributing

Contributions to the Data Tracker app are welcome! If you encounter any issues or have suggestions for improvements, please create an issue in the repository.

## License

The Data Tracker app is released under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Credits

The Data Tracker app is developed by FatemeZahra Bakhshandeh, and Arshia Ariannejad.
