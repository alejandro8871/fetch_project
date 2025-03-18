# Fetch Hiring App

This is an Android application built using **Kotlin**, **Jetpack Compose**, and **MVVM Architecture**. The app fetches a list of hiring data from a remote API, caches it locally, and displays it in a structured format. It also includes error handling and state management using a `sealed class`.

## Features

- Fetch hiring data from a remote API.
- Display hiring items sorted by `listId` and numerical value in `name`.
- Caches previously fetched data to show in case of network failures.
- Implements **MVVM (Model-View-ViewModel)** for separation of concerns.
- Uses **Jetpack Compose** for UI.
- Uses **Hilt** for dependency injection.
- Implements **Mockk** for unit testing.

## Tech Stack

### Languages & Frameworks

- **Kotlin** - Main programming language.
- **Jetpack Compose** - Declarative UI framework.
- **Coroutines & Flow** - For asynchronous operations.

### Architecture

- **MVVM (Model-View-ViewModel)**
- **Repository Pattern** for data management.
- **Sealed Class** for UI state handling.

---

## Installation & Setup

### Prerequisites

- **Android Studio (Latest version)**
- **JDK 11 or higher**
- **Gradle 8+**
- **Internet Connection** (for fetching data)

### Clone the Repository

```sh
git clone https://github.com/your-username/fetch-hiring-app.git
cd fetch-hiring-app
