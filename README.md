# Coop3

Coop3 is an Android application demo that demonstrates the implementation of pull-to-refresh and swipe-to-dismiss functionalities in Jetpack Compose.

## Getting Started

Follow these instructions to set up and run the Coop3 project locally.

### Prerequisites

* Android Studio 

### Installation

1. Clone the repository: https://github.com/Pu-zhao/Coop3-PullToRefresh.git
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the application.

## Features

* **Pull-to-Refresh:** Refresh the list of items by swiping down from the top.
* **Swipe-to-Dismiss Refresh:** Refresh the value of an individual item by swiping it from right to left.
* **Dynamic Data:** Item data is randomly generated and updated on refresh.
* **Modern UI:** Built with Jetpack Compose, featuring a Material 3 design.

## Tech Stack

* **Jetpack Compose:** Modern toolkit for building native UI.
* **Kotlin:** Programming language for Android development.
* **Material 3:** Latest Material Design implementation for Compose.
* **PullToRefreshContainer:** Compose component for implementing pull-to-refresh.
* **SwipeToDismissBox:** Compose component for implementing swipe-to-dismiss.
* **Coroutines:** For managing background tasks, such as refreshing data.

## Project Structure

* `MainActivity.kt`: Contains the `RefreshDemo` composable function, which sets up the pull-to-refresh and displays the list of items.
* `ItemCard.kt`: Defines the `ItemCard` composable function, which displays an individual item and its swipe-to-dismiss refresh functionality.
* `Item.kt`: Defines the `Item` data class and `generateItems` function for creating sample data.


