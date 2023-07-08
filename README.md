# Mobile-Data-Tracker App

Data Tracker is an Android application that helps you monitor and track your mobile data usage and provides users with insights into their data usage patterns. With this app, you can set limits for your data usage, view your daily and monthly usage, View the graphical representation of your data usage pattern. and receive notifications when you reach your set limit.

## Features

- Usage tracking: Monitor and track your daily and monthly mobile data usage.
- Set data usage limit: Set a limit for your daily mobile data usage.
- Notifications: Receive notifications when you reach your set data usage limit.
- Data usage history: View your data usage history for the past 30 days.
- Graphical representation: Visualize your data usage with charts and graphs.
- Analyze data consumation: compare the daily data consumption with the average data consumed in the last 30 days, providing users with insights into their usage habits.

## Installation

To use the Data Tracker app, follow these steps:

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an Android device or emulator.

## Usage

Upon launching the app, you will see the main screen with the following options:

- **Set Limit**: Tap the upper left button to set your data usage limit for the day.
- **Graph**: Tap the upper right button to view a graphical representation of your data usage.
- **Data Usages**: This section displays your current daily data usage, including upload and download speeds.
- **Monthly Data Usages**: This section shows a list of your data usage history for the past 30 days.
- **Analize Data Usage**: Tap on the today's data usage to see your data analysis for the day.

## Screenshots

screenshots

## Libraries Used

The Data Tracker app utilizes the following libraries:

- `dev.jahidhasanco.networkusage`: A library for tracking network usage statistics.
- `com.github.PhilJay:MPAndroidChart`: A powerful charting library for Android.

## Implementation

The Data Tracker app is implemented using Kotlin and follows the Model-View-ViewModel (MVVM) architecture. It utilizes various Android components and libraries, including:

- Android Architecture Components: LiveData, ViewModel, and Room Database.
- Android Data Binding: Used for binding UI components to data.
- RecyclerView: Used for displaying the list of monthly data usages.
- MPAndroidChart: Used for displaying the bar chart representing the data usage for the past 7 days.
- SharedPreferences: Used for storing user preferences, such as the data usage limit and notification settings.

The app makes use of a `NetworkMonitorService` to monitor internet usage in the background and a `ProfilePreferences` object to store and retrieve user preferences.

## Files Explanation

The Data Tracker app consists of the following files:

- **MainActivity.kt**: This file contains the implementation of the main screen of the app. It handles the UI interactions and displays the various sections, including daily data usage, monthly data usages, and buttons for setting the limit and viewing the graph.

- **SetLimitActivity.kt**: This file implements the activity for setting the data usage limit. It provides an input field for users to enter their desired limit and handles the submission of the limit.

- **NetworkMonitorService.kt**: This file includes the implementation of a background service that monitors internet usage. It periodically checks the data usage and sends notifications if the limit is exceeded.

- **ProfilePreferences.kt**: This file defines a shared preferences object for storing user preferences, such as the data usage limit and notification settings.

- **activity_chart.xml**: This XML layout file defines the layout for the activity that displays the graphical representation of data usage.

- **activity_main.xml**: This XML layout file defines the layout for the main screen of the app, including the sections for daily data usage and monthly data usages.

- **activity_set_limit.xml**: This XML layout file defines the layout for the activity that allows users to set the data usage limit.

- **item_data_usage.xml**: This XML layout file defines the layout for each item in the list of monthly data usages.

Please note that this is a general explanation of the files in your project. You can provide more detailed explanations and descriptions based on your specific implementation.


## Contributing

Contributions to the Data Tracker app are welcome! If you encounter any issues or have suggestions for improvements, please create an issue in the repository.

## License

The Data Tracker app is released under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Credits

The Data Tracker app is developed by FatemeZahra Bakhshandeh, and Arshia Ariannejad.